package microservicio.administracion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mantenimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoMantenimiento tipo;
}
