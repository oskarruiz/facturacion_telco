package com.telco.facturacion.controller;

import com.telco.facturacion.entity.Operador;
import com.telco.facturacion.entity.Recarga;
import com.telco.facturacion.service.RecargaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recargas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecargaController {
    
    private final RecargaService recargaService;
    
    @GetMapping("/activas")
    public ResponseEntity<List<Recarga>> obtenerRecargasActivas() {
        List<Recarga> recargas = recargaService.obtenerRecargasActivas();
        return ResponseEntity.ok(recargas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recarga> obtenerRecarga(@PathVariable Long id) {
        try {
            Recarga recarga = recargaService.obtenerRecargaPorId(id);
            return ResponseEntity.ok(recarga);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/operador/{idOperador}")
    public ResponseEntity<List<Recarga>> obtenerRecargasPorOperador(@PathVariable Long idOperador) {
        try {
            List<Recarga> recargas = recargaService.obtenerRecargasPorOperador(idOperador);
            return ResponseEntity.ok(recargas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/operadores")
    public ResponseEntity<List<Operador>> obtenerOperadores() {
        List<Operador> operadores = recargaService.obtenerOperadoresActivos();
        return ResponseEntity.ok(operadores);
    }
}
