package microservicio.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.usuario.entity.Cuenta;

@Repository("CuentaRepository")
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
