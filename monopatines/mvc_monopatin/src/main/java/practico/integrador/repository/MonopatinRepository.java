package practico.integrador.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practico.integrador.entity.EstadoMonopatin;
import practico.integrador.entity.Monopatin;

import java.util.List;

public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    List<Monopatin> findByEstado(EstadoMonopatin estado);

    @Query("SELECT m FROM Monopatin m WHERE m.paradaActual.id = :paradaId")
    List<Monopatin> findByParadaId(Long paradaId);
}
