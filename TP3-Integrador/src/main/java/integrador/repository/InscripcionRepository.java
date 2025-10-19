package integrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import integrador.entity.Inscripcion;

@Repository("InscripcionRepository")
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    @Query(
        value = 
        "select u.nombre, u.anio, sum(u.inscriptos) inscriptos, sum(u.egresados) egresados " +
        "from ( " +
        "   select c.nombre nombre, i.inscripcion anio, count(*) inscriptos, 0 egresados " +
        "   from inscripcion i join carrera c ON c.id = i.carrera " +
        "   GROUP BY c.nombre, i.inscripcion " +
        "   UNION " +
        "   select c.nombre nombre, i.graduacion anio, 0 inscriptos, count(*) egresados " +
        "   from inscripcion i join carrera c ON c.id = i.carrera " +
        "   GROUP BY c.nombre, i.graduacion " +
        "   HAVING anio IS NOT NULL " +
        ") u " +
        "GROUP BY u.nombre, u.anio " +
        "ORDER BY u.nombre ASC, u.anio ASC", nativeQuery = true)        
    List<Object[]> getReport();
}