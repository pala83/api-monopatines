package integrador.dto.carrera;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarreraConEstudiantesDTO {
    private String nombre;
    private Long cant_estudiantes;
}
