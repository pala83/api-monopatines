package microservicio.viaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.viaje.entity.Viaje;

@Repository("ViajeRepository")
public interface ViajeRepository extends JpaRepository<Viaje, Long>{

}
