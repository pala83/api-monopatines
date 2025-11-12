package microservicio.monopatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import microservicio.monopatin.entity.Parada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
    @Query("SELECT p FROM Parada p JOIN p.monopatines m WHERE m.id = :idmonopatin")
    Optional<Parada> findMonopatinById(@Param("idmonopatin")Long id);
}