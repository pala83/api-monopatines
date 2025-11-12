package microservicio.facturacion.repository;

import microservicio.facturacion.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("TarifaRepository")
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

//    @Query("SELECT t FROM Tarifa t WHERE t.fechaInicioVigencia <= :fecha ORDER BY t.fechaInicioVigencia DESC")
//    List<Tarifa> findVigentesHasta(@Param("fecha") LocalDateTime fecha);
}
