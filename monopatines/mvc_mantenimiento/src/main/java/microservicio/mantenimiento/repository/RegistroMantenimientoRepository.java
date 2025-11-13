package microservicio.mantenimiento.repository;

import microservicio.mantenimiento.entity.RegistroMantenimiento;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("registroMantenimientoRepository")
public interface RegistroMantenimientoRepository extends MongoRepository<RegistroMantenimiento, Long> {

}
