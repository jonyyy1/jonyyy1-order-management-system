package com.keola.ordermanagement.service;

import com.keola.ordermanagement.model.Customer;
import com.keola.ordermanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Mono<Customer> getCustomerById(Long ID) {
        return customerRepository.findById(ID);
    }
}
