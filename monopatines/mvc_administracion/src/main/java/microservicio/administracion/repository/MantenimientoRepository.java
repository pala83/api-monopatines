package microservicio.administracion.repository;

import microservicio.administracion.entity.Mantenimiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MantenimientoRepository extends MongoRepository<Mantenimiento, String> {
    List<Mantenimiento> findByIdMonopatin(Long idMonopatin);
}
