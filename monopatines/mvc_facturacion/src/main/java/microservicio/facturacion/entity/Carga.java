package microservicio.facturacion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Carga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "viaje_id", nullable = false)
    private Long viajeId;
    @Column(name = "cuenta_id", nullable = false)
    private Long cuentaId;

    @Column(name = "monto_total")
    private Long montoTotal = 0L;
    @Column(name = "carga_normal")
    private Long cargaNormal = 0L;
    @Column(name = "carga_pausa_extendida")
    private Long cargaPausaExtendida = 0L;

    @Column(name = "duracion_minutos")
    private Long duracionMinutos = 0L;
    @Column(name = "duracion_pausa_minutos")
    private Long duracionPausaMinutos = 0L;

    @Column(name = "km_recorridos")
    private Double kmRecorridos = 0.0;

    @Column(name = "fecha_carga")
    private LocalDateTime fechaCarga = LocalDateTime.now();
}
