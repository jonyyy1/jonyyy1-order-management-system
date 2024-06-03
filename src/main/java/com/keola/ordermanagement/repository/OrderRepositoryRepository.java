package com.keola.ordermanagement.repository;

import com.keola.ordermanagement.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface OrderRepositoryRepository extends ReactiveMongoRepository<Order,String> {
    Mono<Order> findById(String id);
}
