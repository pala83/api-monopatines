package microservicio.mantenimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.mantenimiento.entity.ControlMantenimiento;

import java.util.List;

@Repository("controlMantenimientoRepository")
public interface ControlMantenimientoRepository extends JpaRepository<ControlMantenimiento, Long> {
    List<ControlMantenimiento> findByActivo(Boolean activo);
}
