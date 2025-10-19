package integrador.dto.inscripcion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscripcionReporteDTO {
    private String nombreCarrera;
    private Integer anio;
    private Long inscriptos;
    private Long egresados;
}
