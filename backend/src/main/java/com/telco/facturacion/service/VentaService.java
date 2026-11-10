package com.telco.facturacion.service;

import com.telco.facturacion.dto.ReporteDTO;
import com.telco.facturacion.dto.VentaDTO;
import com.telco.facturacion.dto.VentaResponse;
import com.telco.facturacion.entity.Recarga;
import com.telco.facturacion.entity.Usuario;
import com.telco.facturacion.entity.Venta;
import com.telco.facturacion.repository.RecargaRepository;
import com.telco.facturacion.repository.UsuarioRepository;
import com.telco.facturacion.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {
    
    private final VentaRepository ventaRepository;
    private final RecargaRepository recargaRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public VentaResponse procesarVenta(VentaDTO ventaDTO) {
        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Validar recarga
        Recarga recarga = recargaRepository.findById(ventaDTO.getIdRecarga())
                .orElseThrow(() -> new RuntimeException("Recarga no encontrada"));
        
        if (!recarga.getActivo()) {
            throw new RuntimeException("La recarga no está activa");
        }
        
        // Crear venta
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setRecarga(recarga);
        venta.setOperador(recarga.getOperador());
        venta.setNumeroCelular(ventaDTO.getNumeroCelular());
        venta.setValor(recarga.getValor());
        venta.setComision(recarga.getComision());
        venta.setObservaciones(ventaDTO.getObservaciones());
        venta.setEstado("COMPLETADA");
        
        venta = ventaRepository.save(venta);
        
        return VentaResponse.builder()
                .idVenta(venta.getIdVenta())
                .referenciaTransaccion(venta.getReferenciaTransaccion())
                .valor(venta.getValor())
                .comision(venta.getComision())
                .nombreOperador(venta.getOperador().getNombre())
                .numeroCelular(venta.getNumeroCelular())
                .fechaVenta(venta.getFechaVenta())
                .estado(venta.getEstado())
                .mensaje("Venta realizada exitosamente")
                .build();
    }
    
    public List<Venta> consultarVentas(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return ventaRepository.findAll();
        }
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }
    
    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }
    
    public List<ReporteDTO> obtenerReportePorOperador(Date fechaInicio, Date fechaFin) {
        List<Object[]> resultados = ventaRepository.obtenerReportePorOperador(fechaInicio, fechaFin);
        
        return resultados.stream()
                .map(r -> ReporteDTO.builder()
                        .nombre((String) r[0])
                        .cantidadVentas(((Number) r[1]).longValue())
                        .totalVentas((BigDecimal) r[2])
                        .totalComisiones((BigDecimal) r[3])
                        .fechaInicio(fechaInicio)
                        .fechaFin(fechaFin)
                        .build())
                .collect(Collectors.toList());
    }
    
    public List<ReporteDTO> obtenerReportePorVendedor(Date fechaInicio, Date fechaFin) {
        List<Object[]> resultados = ventaRepository.obtenerReportePorVendedor(fechaInicio, fechaFin);
        
        return resultados.stream()
                .map(r -> ReporteDTO.builder()
                        .nombre((String) r[0])
                        .nombreCompleto((String) r[1])
                        .cantidadVentas(((Number) r[2]).longValue())
                        .totalVentas((BigDecimal) r[3])
                        .totalComisiones((BigDecimal) r[4])
                        .fechaInicio(fechaInicio)
                        .fechaFin(fechaFin)
                        .build())
                .collect(Collectors.toList());
    }
}
