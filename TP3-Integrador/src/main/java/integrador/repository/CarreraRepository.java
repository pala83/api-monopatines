package integrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import integrador.dto.carrera.CarreraConEstudiantesDTO;
import integrador.entity.Carrera;

@Repository("CarreraRepository")
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
	@Query(
        "select new integrador.dto.carrera.CarreraConEstudiantesDTO(c.nombre, count(i)) " +
        "from Carrera c " +
        "left join c.inscripciones i " +
        "group by c.nombre " +
        "order by count(i) desc"
    )
	List<CarreraConEstudiantesDTO> findCarrerasConInscriptosOrdenado();

}
