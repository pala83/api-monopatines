package microservicio.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.facturacion.entity.Carga;

@Repository("CargaRepository")
public interface CargaRepository extends JpaRepository<Carga, Long> {

}
