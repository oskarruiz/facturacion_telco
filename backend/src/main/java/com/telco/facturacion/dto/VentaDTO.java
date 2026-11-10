package com.telco.facturacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    
    @NotNull(message = "El ID de la recarga es obligatorio")
    private Long idRecarga;
    
    @NotBlank(message = "El número celular es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número celular debe tener 10 dígitos")
    private String numeroCelular;
    
    private String observaciones;
}
