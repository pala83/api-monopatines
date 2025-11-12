package microservicio.viaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.viaje.entity.Pausa;

@Repository("PausaRepository")
public interface PausaRepository extends JpaRepository<Pausa, Long> {

}
