package com.keola.ordermanagement.service;

import com.keola.ordermanagement.exception.*;
import com.keola.ordermanagement.model.*;
import com.keola.ordermanagement.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase es el servicio principal para la gestión de pedidos en la aplicación.
 * Proporciona métodos para crear, actualizar, eliminar y obtener pedidos.
 *
 * Los métodos de esta clase son transaccionales para garantizar la consistencia de los datos.
 * En caso de error durante la creación o actualización de un pedido, se revertirán los cambios.
 */
@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepositoryRepository orderRepositoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Mono<Order> createOrder(@Valid Order order) {
        List<Product> productosOriginales = new ArrayList<>();

        return customerRepository.findById(order.getCustomer().getId())
            .switchIfEmpty(Mono.error(new CustomerNotFoundException(order.getCustomer().getId())))
            .flatMap(cliente -> {
                order.setCustomer(cliente);
                return validarYActualizarProductos(order, productosOriginales)
                    .flatMap(itemPedidos -> {
                        order.setItemOrders(itemPedidos);
                        return orderRepositoryRepository.save(order);
                    })
                    .onErrorResume(e -> revertirCambios(productosOriginales, e));
            });
    }

    @Transactional
    public Mono<Order> updateOrder(String id, @Valid Order order) {
        List<Product> productosOriginales = new ArrayList<>();

        return orderRepositoryRepository.findById(id)
            .switchIfEmpty(Mono.error(new OrderNotFoundException(id)))
            .flatMap(existingPedido -> {
                return validarYActualizarProductosEnUpdate(order, existingPedido, productosOriginales)
                    .flatMap(itemPedidos -> {
                        existingPedido.setItemOrders(itemPedidos);
                        existingPedido.setState(order.getState());
                        existingPedido.setCreationDate(order.getCreationDate());
                        return orderRepositoryRepository.save(existingPedido);
                    })
                    .onErrorResume(e -> revertirCambios(productosOriginales, e));
            });
    }

    private Mono<List<ItemOrder>> validarYActualizarProductos(Order order, List<Product> productosOriginales) {
        List<Mono<ItemOrder>> itemPedidoMonos = order.getItemOrders().stream()
            .map(itemOrder -> productRepository.findById(itemOrder.getProduct().getId())
                .switchIfEmpty(Mono.error(new ProductNotFoundException(itemOrder.getProduct().getId())))
                .flatMap(product -> {
                    productosOriginales.add(product.toBuilder().build());
                    if (product.getStock() < itemOrder.getQuantity()) {
                        return Mono.error(new StockInsuficienteException(product.getId()));
                    }
                    product.setStock(product.getStock() - itemOrder.getQuantity());
                    return productRepository.save(product)
                        .flatMap(productGuardado -> {
                            itemOrder.setProduct(productGuardado);
                            return Mono.just(itemOrder);
                        });
                }))
            .collect(Collectors.toList());

        return Flux.concat(itemPedidoMonos).collectList();
    }

    private Mono<List<ItemOrder>> validarYActualizarProductosEnUpdate(Order order, Order existingOrder, List<Product> productosOriginales) {
        List<Mono<ItemOrder>> itemPedidoMonos = order.getItemOrders().stream()
            .map(itemOrder -> Flux.fromIterable(existingOrder.getItemOrders())
                .filter(existingItemOrder -> existingItemOrder.getProduct().getId().equals(itemOrder.getProduct().getId()))
                .singleOrEmpty()
                .flatMap(existingItemOrder -> productRepository.findById(existingItemOrder.getProduct().getId())
                    .flatMap(product -> {
                        productosOriginales.add(product.toBuilder().build());
                        int nuevaCantidad = product.getStock() + (existingItemOrder.getQuantity() - itemOrder.getQuantity());
                        if (nuevaCantidad < 0) {
                            return Mono.error(new StockInsuficienteException(product.getId()));
                        }
                        product.setStock(nuevaCantidad);
                        existingItemOrder.setQuantity(itemOrder.getQuantity());
                        existingItemOrder.setProduct(product);
                        return productRepository.save(product)
                            .then(Mono.just(existingItemOrder));
                    })))
            .collect(Collectors.toList());

        return Flux.concat(itemPedidoMonos).collectList();
    }

    private Mono<Order> revertirCambios(List<Product> productosOriginales, Throwable e) {
        List<Mono<Product>> operacionesReversion = productosOriginales.stream()
            .map(productOriginal -> productRepository.findById(productOriginal.getId())
                .flatMap(product -> {
                    product.setStock(productOriginal.getStock());
                    return productRepository.save(product);
                }))
            .collect(Collectors.toList());

        return Flux.concat(operacionesReversion)
            .collectList()
            .then(Mono.error(new RuntimeException("Error durante la creación/actualización del pedido. Los cambios han sido revertidos. Causa original: " + e.getMessage(), e)));
    }

    @Override
    public Mono<Void> deleteOrder(String id) {
        return orderRepositoryRepository.deleteById(id);
    }

    @Override
    public Mono<Order> getOrder(String id) {
        return orderRepositoryRepository.findById(id)
            .switchIfEmpty(Mono.error(new OrderNotFoundException(id)));
    }

    @Override
    public Flux<Order> getAllOrders() {
        return orderRepositoryRepository.findAll();
    }

}
