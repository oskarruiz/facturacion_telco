package com.telco.facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {
    
    private Long idVenta;
    private String referenciaTransaccion;
    private BigDecimal valor;
    private BigDecimal comision;
    private String nombreOperador;
    private String numeroCelular;
    private Date fechaVenta;
    private String estado;
    private String mensaje;
}
