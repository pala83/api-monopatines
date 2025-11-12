package microservicio.viaje.dto.viaje;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.viaje.entity.EstadoViaje;
import microservicio.viaje.entity.Ubicacion;

@Data
@RequiredArgsConstructor
public class ViajeRequest {
    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Long usuarioId;
    @NotNull(message = "El ID de cuenta no puede ser nulo")
    private Long cuentaId;
    @NotNull(message = "El ID de monopatín no puede ser nulo")
    private Long monopatinId;
    private LocalDateTime inicio = LocalDateTime.now();
    private LocalDateTime fin = null;
    @NotNull(message = "La ubicación de origen no puede ser nula")
    private Ubicacion origen;
    @NotNull(message = "La ubicación de destino no puede ser nula")
    private Ubicacion destino;
    @DecimalMin(value = "0.0", inclusive = true, message = "La distancia recorrida no puede ser negativa")
    private double distanciaRecorrida = 0.0;
    private EstadoViaje estado = EstadoViaje.EN_CURSO;

}
