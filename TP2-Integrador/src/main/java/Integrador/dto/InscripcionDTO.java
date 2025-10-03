package Integrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InscripcionDTO {
    private Integer id;
    private Integer id_estudiante;
    private Integer id_carrera;
    private Integer inscripcion;
    private Integer graduacion;
    private Integer antiguedad;
}
