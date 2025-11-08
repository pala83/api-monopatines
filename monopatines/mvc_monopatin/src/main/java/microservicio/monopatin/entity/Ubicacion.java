package microservicio.monopatin.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Ubicacion {
    private Double latitud;
    private Double longitud;
}
