package microservicio.viaje.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Pausa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pausa")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    @Column(name = "tiempo_inicio")
    private LocalDateTime tiempoInicio = LocalDateTime.now();
    @Column(name = "tiempo_fin")
    private LocalDateTime tiempoFin;

    @Column(name = "duracion_segundos")
    private Long duracionSegundos = 0L;
    @Column(name = "extendido")
    private Boolean extendido = false;
}
