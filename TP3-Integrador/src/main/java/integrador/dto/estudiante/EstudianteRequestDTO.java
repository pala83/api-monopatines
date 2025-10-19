package integrador.dto.estudiante;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class EstudianteRequestDTO {
    @Min(value=0, message = "El DNI debe ser un número positivo")
    @NotNull(message = "El DNI no puede ser nulo")
    private Long dni;
    @NotNull(message = "El Nombre no puede ser nulo")
    @NotEmpty(message = "El Nombre no puede estar vacío")
    private String nombre;
    @NotNull(message = "El Apellido no puede ser nulo")
    @NotEmpty(message = "El Apellido no puede estar vacío")
    private String apellido;
    @Min(value=0, message = "La Edad debe ser un número positivo")
    private Integer edad;
    @NotNull(message = "El Género no puede ser nulo")
    private String genero;
    @NotNull(message = "La Ciudad no puede ser nula")
    private String ciudad;
    @Min(value=0, message = "El Legajo debe ser un número positivo")
    @NotNull(message = "El Legajo no puede ser nulo")
    private Integer lu;
}