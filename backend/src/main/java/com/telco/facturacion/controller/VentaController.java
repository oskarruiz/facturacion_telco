package com.telco.facturacion.controller;

import com.telco.facturacion.dto.ReporteDTO;
import com.telco.facturacion.dto.VentaDTO;
import com.telco.facturacion.dto.VentaResponse;
import com.telco.facturacion.entity.Venta;
import com.telco.facturacion.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {
    
    private final VentaService ventaService;
    
    @PostMapping("/realizar")
    public ResponseEntity<VentaResponse> realizarVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        try {
            VentaResponse response = ventaService.procesarVenta(ventaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(VentaResponse.builder()
                            .mensaje("Error al procesar la venta: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Venta>> consultarVentas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        
        List<Venta> ventas = ventaService.consultarVentas(fechaInicio, fechaFin);
        return ResponseEntity.ok(ventas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id) {
        try {
            Venta venta = ventaService.obtenerVentaPorId(id);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/reporte/operador")
    public ResponseEntity<List<ReporteDTO>> reportePorOperador(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        
        List<ReporteDTO> reporte = ventaService.obtenerReportePorOperador(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
    
    @GetMapping("/reporte/vendedor")
    public ResponseEntity<List<ReporteDTO>> reportePorVendedor(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        
        List<ReporteDTO> reporte = ventaService.obtenerReportePorVendedor(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
