package practico.integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practico.integrador.entity.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}