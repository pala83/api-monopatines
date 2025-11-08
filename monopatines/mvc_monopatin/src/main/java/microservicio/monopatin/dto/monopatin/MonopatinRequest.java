package microservicio.monopatin.dto.monopatin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Ubicacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinRequest {
    @NotNull(message = "La marca no puede ser nula")
    private String marca;

    @NotNull(message = "El código QR no puede ser nulo")
    @Size(min = 3)
    private String codigoQR;

    @DecimalMin("0.0")
    private Double kmTotales;

    @NotNull(message = "El estado no puede ser nulo")
    private EstadoMonopatin estado;

    @NotNull(message = "La ubicación actual no puede ser nula")
    private Ubicacion ubicacionActual;

    private Long paradaActualId; // opcional
}

