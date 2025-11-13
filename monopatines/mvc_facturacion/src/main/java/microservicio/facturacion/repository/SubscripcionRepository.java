package microservicio.facturacion.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservicio.facturacion.dto.subscripcion.SubscripcionResponse;
import microservicio.facturacion.entity.Subscripcion;

@Repository("SubscripcionRepository")
public interface SubscripcionRepository extends JpaRepository<Subscripcion, Long> {

    // Obtiene las subscripciones vigentes para una cuenta en una fecha específica.
    @Query("SELECT new microservicio.facturacion.dto.subscripcion.SubscripcionResponse(s.idCuenta, s.periodo, s.fechaPago, s.monto, s.estado, s.vigencia) " + 
            "FROM Subscripcion s " +
            "WHERE s.idCuenta = :idCuenta " +
                "AND s.vigencia.inicio <= :fecha " +
                "AND (s.vigencia.fin IS NULL OR s.vigencia.fin >= :fecha)")
    List<SubscripcionResponse> findSubscripcionesVigentes(
        @Param("idCuenta") Long idCuenta, 
        @Param("fecha") LocalDate fecha);
    // Obtiene todas las subscripciones de una cuenta.
    List<Subscripcion> findByIdCuenta(Long idCuenta);
}
