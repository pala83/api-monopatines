package microservicio.facturacion.repository;

import microservicio.facturacion.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdCuenta(Long idCuenta);

    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :desde AND :hasta")
    List<Pago> findBetweenDates(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.fechaPago BETWEEN :desde AND :hasta")
    Double totalBetweenDates(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}
