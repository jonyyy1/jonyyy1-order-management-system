package com.keola.ordermanagement;

import com.keola.ordermanagement.exception.CustomerNotFoundException;
import com.keola.ordermanagement.exception.OrderNotFoundException;
import com.keola.ordermanagement.model.*;
import com.keola.ordermanagement.repository.CustomerRepository;
import com.keola.ordermanagement.repository.OrderRepositoryRepository;
import com.keola.ordermanagement.repository.ProductRepository;
import com.keola.ordermanagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataMongoTest
public class OrderServiceTest {

    @Mock
    private OrderRepositoryRepository orderRepositoryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService pedidoService;

    private Customer customer;
    private Product product1;
    private Product product2;
    private ItemOrder itemOrder1;
    private ItemOrder itemOrder2;
    private Order existingOrder;
    private Order updatedOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Test Cliente");
        customer.setAddress("123 Test St");
        customer.setEmail("test@example.com");

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Producto 1");
        product1.setPrice(new BigDecimal("100.00"));
        product1.setStock(10);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Producto 2");
        product2.setPrice(new BigDecimal("200.00"));
        product2.setStock(20);

        itemOrder1 = new ItemOrder();
        itemOrder1.setProduct(product1);
        itemOrder1.setQuantity(2);

        itemOrder2 = new ItemOrder();
        itemOrder2.setProduct(product2);
        itemOrder2.setQuantity(5);

        Date fechaActual = new Date();
        Date fechaEntrega = new Date(fechaActual.getTime() + 86400000); // +1 día

        existingOrder = new Order();
        existingOrder.set_id("1");
        existingOrder.setCustomer(customer);
        existingOrder.setItemOrders(List.of(itemOrder1, itemOrder2));
        existingOrder.setCreationDate(fechaActual);
        existingOrder.setUpdateDate(fechaActual);
        existingOrder.setDeadline(fechaEntrega);
        existingOrder.setState("estado 1");

        updatedOrder = new Order();
        updatedOrder.set_id("1");
        updatedOrder.setCustomer(customer);
        updatedOrder.setItemOrders(List.of(
            new ItemOrder(product1, 3),
            new ItemOrder(product2, 15)
        ));
        updatedOrder.setCreationDate(fechaActual);
        updatedOrder.setUpdateDate(fechaActual);
        updatedOrder.setDeadline(fechaEntrega);
        updatedOrder.setState("estado 1");

    }

    @Test
    public void testCreateOrUpdatePedido_Success() {
        when(customerRepository.findById(1L)).thenReturn(Mono.just(customer));
        when(productRepository.findById(1L)).thenReturn(Mono.just(product1));
        when(productRepository.findById(2L)).thenReturn(Mono.just(product2));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product1), Mono.just(product2));
        when(orderRepositoryRepository.save(any(Order.class))).thenReturn(Mono.just(existingOrder));

        Mono<Order> result = pedidoService.createOrder(existingOrder);

        StepVerifier.create(result)
            .expectNextMatches(pedidoResult -> pedidoResult.getCustomer().getId().equals(1L) &&
                                                pedidoResult.getItemOrders().size() == 2 &&
                                                pedidoResult.getItemOrders().get(0).getQuantity() == 2 &&
                                                pedidoResult.getItemOrders().get(1).getQuantity() == 5)
            .verifyComplete();
    }

    @Test
    public void testUpdatePedido_Success() {
        when(orderRepositoryRepository.findById("1")).thenReturn(Mono.just(existingOrder));
        when(productRepository.findById(1L)).thenReturn(Mono.just(product1));
        when(productRepository.findById(2L)).thenReturn(Mono.just(product2));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product1), Mono.just(product2));
        when(orderRepositoryRepository.save(any(Order.class))).thenReturn(Mono.just(updatedOrder));

        Mono<Order> result = pedidoService.updateOrder("1", updatedOrder);

        StepVerifier.create(result)
            .expectNextMatches(pedidoResult -> pedidoResult.getCustomer().getId().equals(1L) &&
                                                pedidoResult.getItemOrders().size() == 2 &&
                                                pedidoResult.getItemOrders().get(0).getQuantity() == 3 &&
                                                pedidoResult.getItemOrders().get(1).getQuantity() == 15)
            .verifyComplete();
    }


    @Test
    public void testCreateOrUpdatePedido_ClienteNoEncontrado() {
        when(customerRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Order> result = pedidoService.createOrder(existingOrder);

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof CustomerNotFoundException &&
                                             throwable.getMessage().contains("Cliente no encontrado"))
            .verify();
    }


    @Test
    public void testDeletePedido_Success() {
        when(orderRepositoryRepository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> result = pedidoService.deleteOrder("1");

        StepVerifier.create(result)
            .verifyComplete();
    }

    @Test
    public void testGetPedido_Success() {
        when(orderRepositoryRepository.findById("1")).thenReturn(Mono.just(existingOrder));

        Mono<Order> result = pedidoService.getOrder("1");

        StepVerifier.create(result)
            .expectNextMatches(pedidoResult -> pedidoResult.getCustomer().getId().equals(1L) &&
                                                pedidoResult.getItemOrders().size() == 2 &&
                                                pedidoResult.getItemOrders().get(0).getQuantity() == 2 &&
                                                pedidoResult.getItemOrders().get(1).getQuantity() == 5)
            .verifyComplete();
    }

    @Test
    public void testGetPedido_NotFound() {
        when(orderRepositoryRepository.findById("1")).thenReturn(Mono.empty());

        Mono<Order> result = pedidoService.getOrder("1");

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof OrderNotFoundException &&
                                             throwable.getMessage().contains("Pedido no encontrado"))
            .verify();
    }
}
