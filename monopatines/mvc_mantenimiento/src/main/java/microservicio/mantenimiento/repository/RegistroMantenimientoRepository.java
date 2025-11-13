package microservicio.mantenimiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.mantenimiento.entity.RegistroMantenimiento;

@Repository("registroMantenimientoRepository")
public interface RegistroMantenimientoRepository extends JpaRepository<RegistroMantenimiento, Long> {
	List<RegistroMantenimiento> findByIdMonopatinAndFechaFinIsNull(Long idMonopatin);
	List<RegistroMantenimiento> findByIdMonopatin(Long idMonopatin);
}
