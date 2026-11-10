package com.telco.facturacion.repository;

import com.telco.facturacion.entity.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {
    
    List<Operador> findByActivoTrue();
    
    Optional<Operador> findByCodigo(String codigo);
    
    Optional<Operador> findByNombre(String nombre);
}
