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
public class ReporteDTO {
    
    private String nombre;
    private String nombreCompleto;
    private Long cantidadVentas;
    private BigDecimal totalVentas;
    private BigDecimal totalComisiones;
    private Date fechaInicio;
    private Date fechaFin;
}
