package com.keola.ordermanagement.dto;

import com.keola.ordermanagement.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    String id;
    Customer customer;
    List<ItemOrderDTO> itemOrderDTOS;
    int total;

}
