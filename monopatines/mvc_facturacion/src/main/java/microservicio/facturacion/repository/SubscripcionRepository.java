package microservicio.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.facturacion.entity.Subscripcion;

@Repository("SubscripcionRepository")
public interface SubscripcionRepository extends JpaRepository<Subscripcion, Long> {

}
