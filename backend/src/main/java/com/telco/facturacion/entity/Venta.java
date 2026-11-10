package com.telco.facturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "VENTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_venta")
    @SequenceGenerator(name = "seq_venta", sequenceName = "SEQ_VENTA", allocationSize = 1)
    @Column(name = "id_venta")
    private Long idVenta;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recarga", nullable = false)
    private Recarga recarga;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_operador", nullable = false)
    private Operador operador;
    
    @Column(name = "numero_celular", nullable = false, length = 15)
    private String numeroCelular;
    
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(name = "comision", precision = 10, scale = 2)
    private BigDecimal comision;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_venta")
    private Date fechaVenta;
    
    @Column(name = "estado", length = 20)
    private String estado; // COMPLETADA, PENDIENTE, FALLIDA
    
    @Column(name = "referencia_transaccion", unique = true, length = 100)
    private String referenciaTransaccion;
    
    @Column(name = "observaciones", length = 500)
    private String observaciones;
    
    @PrePersist
    protected void onCreate() {
        fechaVenta = new Date();
        if (estado == null) {
            estado = "COMPLETADA";
        }
        if (referenciaTransaccion == null) {
            referenciaTransaccion = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if (comision == null) {
            comision = BigDecimal.ZERO;
        }
    }
}
