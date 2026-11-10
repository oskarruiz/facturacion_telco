package com.telco.facturacion.service;

import com.telco.facturacion.entity.Operador;
import com.telco.facturacion.entity.Recarga;
import com.telco.facturacion.repository.OperadorRepository;
import com.telco.facturacion.repository.RecargaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecargaService {
    
    private final RecargaRepository recargaRepository;
    private final OperadorRepository operadorRepository;
    
    public List<Recarga> obtenerRecargasActivas() {
        return recargaRepository.findByActivoTrue();
    }
    
    public Recarga obtenerRecargaPorId(Long id) {
        return recargaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recarga no encontrada"));
    }
    
    public List<Recarga> obtenerRecargasPorOperador(Long idOperador) {
        Operador operador = operadorRepository.findById(idOperador)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));
        
        return recargaRepository.findByOperadorAndActivoTrue(operador);
    }
    
    public List<Operador> obtenerOperadoresActivos() {
        return operadorRepository.findByActivoTrue();
    }
}
