package microservicio.trip.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pausas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pausa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;
    private LocalDateTime fin;

    @ManyToOne
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;
}
