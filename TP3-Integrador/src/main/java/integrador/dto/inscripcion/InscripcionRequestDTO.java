package integrador.dto.inscripcion;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class InscripcionRequestDTO {
    @Min(value = 0, message = "El ID del estudiante debe ser un número positivo")
    @NotNull(message = "El ID del estudiante no puede ser nulo")
    private Long id_estudiante;
    @Min(value = 0, message = "El ID de la carrera debe ser un número positivo")
    @NotNull(message = "El ID de la carrera no puede ser nulo")
    private Long id_carrera;
    @NotNull(message = "El año de inscripción no puede ser nulo")
    @Min(value = 1970, message = "El año de inscripción debe ser mayor o igual a 1970")
    private Integer inscripcion;
    
    private Integer graduacion;
    @NotNull(message = "La antigüedad no puede ser nula")
    private Integer antiguedad;

    @AssertTrue(message = "El año de graduación debe ser igual o posterior al año de inscripción")
    public boolean isGraduacionPosteriorAInscripcion() {
        return graduacion == null || graduacion >= inscripcion;
    }
}
