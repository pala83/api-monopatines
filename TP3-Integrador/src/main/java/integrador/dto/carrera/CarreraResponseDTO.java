package integrador.dto.carrera;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarreraResponseDTO {
    private String nombre;
    private Integer duracion;
}
