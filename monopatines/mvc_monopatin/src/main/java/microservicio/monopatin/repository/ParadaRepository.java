package microservicio.monopatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import microservicio.monopatin.entity.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}