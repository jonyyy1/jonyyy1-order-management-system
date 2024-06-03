package com.keola.ordermanagement.repository;

import com.keola.ordermanagement.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
