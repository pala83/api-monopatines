package integrador.dto.inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscripcionResponseDTO {
    private Long id_estudiante;
    private Long id_carrera;
    private Integer inscripcion;
    private Integer graduacion;
    private Integer antiguedad;
}
