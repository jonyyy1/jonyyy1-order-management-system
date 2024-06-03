package com.keola.ordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder=true)
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;

    // Getters y Setters
}