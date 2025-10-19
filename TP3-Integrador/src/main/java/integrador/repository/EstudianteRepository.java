package integrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import integrador.entity.Estudiante;

@Repository("EstudianteRepository")
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	List<Estudiante> findByGenero(String genero);
    Optional<Estudiante> findByLu(Integer lu);
    List<Estudiante> findByInscripcionesCarreraId(Long carreraId);

    @Query(
        "select e " + 
        "from Estudiante e, in(e.inscripciones) i " +
        "where i.carrera.id = :carreraId " +
        "and lower(e.ciudad) = lower(:ciudad)"
    )
	List<Estudiante> findByCarreraIdAndCiudad(@Param("carreraId") Long carreraId, @Param("ciudad") String ciudad);

}
