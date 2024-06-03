package com.keola.ordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    private Long id;
    private String name;
    private String address;
    private String email;

}