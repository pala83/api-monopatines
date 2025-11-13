package microservicio.monopatin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Monopatin;
import microservicio.monopatin.entity.Parada;

import java.util.List;

public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
    // Buscar monopatines por estado
    @Query("SELECT m FROM Monopatin m WHERE m.estado = :estado")
    List<Monopatin> findByEstado(@Param("estado") EstadoMonopatin estado);
    // Contar monopatines en una parada específica
    long countByParadaActual(Parada parada);
}
