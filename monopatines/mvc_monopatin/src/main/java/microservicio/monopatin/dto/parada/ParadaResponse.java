package microservicio.monopatin.dto.parada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaResponse {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
}
