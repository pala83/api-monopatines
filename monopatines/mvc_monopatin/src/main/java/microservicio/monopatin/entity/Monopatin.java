package microservicio.monopatin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Monopatin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_monopatin")
    private Long id;

    @Column(name = "codigo_qr", unique = true, nullable = false)
    private String codigoQR;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "km_totales")
    private double kmTotales = 0.0;

    @Column(name = "uso_total_minutos")
    private Long usoTotalMinutos = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMonopatin estado;

    @Column(name = "fecha_ultimo_mantenimiento")
    private LocalDateTime fechaUltimoMantenimiento = LocalDateTime.now();

    @Embedded
    @Column(name = "ubicacion")
    private Ubicacion ubicacionActual;

    @ManyToOne
    @JoinColumn(name = "parada_id")
    private Parada paradaActual;
}
