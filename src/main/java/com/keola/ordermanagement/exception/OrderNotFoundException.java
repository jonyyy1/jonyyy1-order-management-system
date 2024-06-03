package com.keola.ordermanagement.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String id) {
        super("Pedido no encontrado con ID: " + id);
    }
}
