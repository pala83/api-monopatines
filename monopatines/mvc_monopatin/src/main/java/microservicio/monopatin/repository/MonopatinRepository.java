package microservicio.monopatin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Monopatin;

import java.util.List;

public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Query("SELECT m FROM Monopatin m WHERE m.estado = :estado")
    List<Monopatin> findByEstado(@Param("estado") EstadoMonopatin estado);

//comentado porque rompetodo la query hay que arreglarla
//    @Query(
//           " SELECT m.* FROM monopatines JOIN paradas p ON m.parada_actual_id = " +
//           "p.idWHERE m.estado = 'DISPONIBLE'ORDER BY POW(p.latitud - :lat, 2) + " +
//           "POW(p.longitud - :lon, 2) LIMIT :limite "
//    )
 //   List<Monopatin> buscarPorCercania(@Param("lat") Double lat, @Param("lon") Double lon, @Param("limite") int limite);

}
