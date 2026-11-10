package com.telco.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "RECARGA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recarga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recarga")
    @SequenceGenerator(name = "seq_recarga", sequenceName = "SEQ_RECARGA", allocationSize = 1)
    @Column(name = "id_recarga")
    private Long idRecarga;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_operador", nullable = false)
    private Operador operador;
    
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "comision", precision = 10, scale = 2)
    private BigDecimal comision;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @PrePersist
    protected void onCreate() {
        if (activo == null) {
            activo = true;
        }
        if (comision == null) {
            comision = BigDecimal.ZERO;
        }
    }
}
