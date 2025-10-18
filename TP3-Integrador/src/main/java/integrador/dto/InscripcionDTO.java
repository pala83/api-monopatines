package integrador.dto;

import integrador.entity.Inscripcion;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InscripcionDTO {
    private Long id;
    private Long id_estudiante;
    private Long id_carrera;
    private Integer inscripcion;
    private Integer graduacion;
    private Integer antiguedad;

    public InscripcionDTO(Inscripcion i) {
        this.id = i.getId();
        this.id_estudiante = i.getEstudiante().getDni();
        this.id_carrera = i.getCarrera().getId();
        this.inscripcion = i.getInscripcion();
        this.graduacion = i.getGraduacion();
        this.antiguedad = i.getAntiguedad();
    }
}
