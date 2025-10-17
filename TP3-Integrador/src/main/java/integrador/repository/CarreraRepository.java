package integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import integrador.entity.Carrera;

@Repository("CarreraRepository")
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

}
