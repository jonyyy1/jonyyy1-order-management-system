package com.keola.ordermanagement.service;

import com.keola.ordermanagement.model.Customer;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<Customer> getCustomerById(Long ID);
}
