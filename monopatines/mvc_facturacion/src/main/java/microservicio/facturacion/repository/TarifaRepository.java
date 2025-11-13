package microservicio.facturacion.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservicio.facturacion.entity.Tarifa;

@Repository("TarifaRepository")
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query("SELECT t " + 
            "FROM Tarifa t " + 
            "WHERE :fecha >= t.vigencia.inicio AND " +
                  "(t.vigencia.fin IS NULL OR :fecha <= t.vigencia.fin)" +
           "ORDER BY t.vigencia.inicio DESC " +
           "LIMIT 1")
    Optional<Tarifa> findTarifaVigente(@Param("fecha") LocalDate fecha);
}
