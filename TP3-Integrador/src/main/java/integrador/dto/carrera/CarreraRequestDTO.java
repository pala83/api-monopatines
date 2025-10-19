package integrador.dto.carrera;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CarreraRequestDTO {
    @Min(value = 0, message = "El ID debe ser un número positivo")
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;
    @NotNull(message = "La carrera no puede ser nula")
    private String nombre;
    @Min(value = 1, message = "La duración debe ser un número entero positivo superior a cero")
    @NotNull(message = "La duración no puede ser nula")
    private Integer duracion;
}
