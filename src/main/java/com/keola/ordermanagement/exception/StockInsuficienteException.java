package com.keola.ordermanagement.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(Long productoId) {
        super("No hay suficiente stock para el producto con ID: " + productoId);
    }
}