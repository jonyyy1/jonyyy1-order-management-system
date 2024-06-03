package com.keola.ordermanagement.service;

import com.keola.ordermanagement.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOrderService {

    Mono<Order> updateOrder(String id, Order order);

    Mono<Order> createOrder(Order order);

    Mono<Void> deleteOrder(String id);

    Mono<Order> getOrder(String id);

    Flux<Order> getAllOrders();
}
