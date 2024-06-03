package com.keola.ordermanagement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private String _id;

    @NotNull(message = "El cliente no puede ser nulo.")
    private Customer customer;

    @Valid
    @NotNull(message = "La lista de items no puede ser nula.")
    private List<ItemOrder> itemOrders;

    @NotNull(message = "La fecha del pedido no puede ser nula.")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura.")
    @CreatedDate
    private Date creationDate;

    @NotNull(message = "La fecha de actualización no puede ser nula.")
    @LastModifiedDate
    private Date updateDate;

    @NotNull(message = "La fecha de entrega no puede ser nula.")
    @FutureOrPresent(message = "La fecha del pedido no puede ser pasada.")
    private Date deadline;

    private String state;

}
