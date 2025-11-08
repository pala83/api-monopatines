package microservicio.monopatin.dto.parada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservicio.monopatin.entity.Ubicacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaRequest {
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotNull(message = "La ubicación no puede ser nula")
    private Ubicacion ubicacion;
    @NotNull(message = "La capacidad no puede ser nula")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;
}
