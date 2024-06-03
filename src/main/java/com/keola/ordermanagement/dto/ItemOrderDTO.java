package com.keola.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrderDTO {

    String nombreProducto;
    int cantidad;
    double precioUnitario;
}
