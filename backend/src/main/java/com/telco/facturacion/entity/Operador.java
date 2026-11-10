package com.telco.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OPERADOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_operador")
    @SequenceGenerator(name = "seq_operador", sequenceName = "SEQ_OPERADOR", allocationSize = 1)
    @Column(name = "id_operador")
    private Long idOperador;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
    
    @Column(name = "codigo", nullable = false, unique = true, length = 10)
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @PrePersist
    protected void onCreate() {
        if (activo == null) {
            activo = true;
        }
    }
}
