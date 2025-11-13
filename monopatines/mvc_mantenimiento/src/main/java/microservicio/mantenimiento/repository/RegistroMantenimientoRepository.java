package microservicio.mantenimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.mantenimiento.entity.RegistroMantenimiento;

@Repository("registroMantenimientoRepository")
public interface RegistroMantenimientoRepository extends JpaRepository<RegistroMantenimiento, Long> {

}
