package com.telco.facturacion.repository;

import com.telco.facturacion.entity.Operador;
import com.telco.facturacion.entity.Usuario;
import com.telco.facturacion.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    List<Venta> findByUsuario(Usuario usuario);
    
    List<Venta> findByOperador(Operador operador);
    
    List<Venta> findByFechaVentaBetween(Date fechaInicio, Date fechaFin);
    
    List<Venta> findByEstado(String estado);
    
    @Query("SELECT v FROM Venta v WHERE v.usuario.idUsuario = :idUsuario " +
           "AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin ORDER BY v.fechaVenta DESC")
    List<Venta> findByUsuarioAndFecha(@Param("idUsuario") Long idUsuario, 
                                      @Param("fechaInicio") Date fechaInicio, 
                                      @Param("fechaFin") Date fechaFin);
    
    @Query("SELECT v FROM Venta v WHERE v.operador.idOperador = :idOperador " +
           "AND v.fechaVenta BETWEEN :fechaInicio AND :fechaFin ORDER BY v.fechaVenta DESC")
    List<Venta> findByOperadorAndFecha(@Param("idOperador") Long idOperador, 
                                       @Param("fechaInicio") Date fechaInicio, 
                                       @Param("fechaFin") Date fechaFin);
    
    @Query("SELECT o.nombre, COUNT(v.idVenta), SUM(v.valor), SUM(v.comision) " +
           "FROM Venta v JOIN v.operador o " +
           "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
           "AND v.estado = 'COMPLETADA' " +
           "GROUP BY o.nombre")
    List<Object[]> obtenerReportePorOperador(@Param("fechaInicio") Date fechaInicio, 
                                             @Param("fechaFin") Date fechaFin);
    
    @Query("SELECT u.username, CONCAT(u.nombre, ' ', u.apellido), COUNT(v.idVenta), " +
           "SUM(v.valor), SUM(v.comision) " +
           "FROM Venta v JOIN v.usuario u " +
           "WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin " +
           "AND v.estado = 'COMPLETADA' " +
           "GROUP BY u.username, u.nombre, u.apellido")
    List<Object[]> obtenerReportePorVendedor(@Param("fechaInicio") Date fechaInicio, 
                                             @Param("fechaFin") Date fechaFin);
}
