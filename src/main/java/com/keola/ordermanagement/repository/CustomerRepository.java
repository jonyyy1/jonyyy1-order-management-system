package com.keola.ordermanagement.repository;

import com.keola.ordermanagement.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    Mono<Customer> findById(Long ID);
}
