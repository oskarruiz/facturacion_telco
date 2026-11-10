package com.telco.facturacion.repository;

import com.telco.facturacion.entity.Operador;
import com.telco.facturacion.entity.Recarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecargaRepository extends JpaRepository<Recarga, Long> {
    
    List<Recarga> findByActivoTrue();
    
    List<Recarga> findByOperadorAndActivoTrue(Operador operador);
    
    List<Recarga> findByOperador_IdOperador(Long idOperador);
}
