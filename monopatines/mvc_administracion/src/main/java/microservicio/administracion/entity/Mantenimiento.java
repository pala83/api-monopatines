package microservicio.administracion.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mantenimientos")
public class Mantenimiento {

    @Id
    private String id;

    @Indexed
    private Long idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String descripcion;

    private TipoMantenimiento tipo;
}
