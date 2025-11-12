package microservicio.viaje.dto.pausa;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PausaRequest {
    private Long viajeId;
    private LocalDateTime tiempoInicio = LocalDateTime.now();
    private LocalDateTime tiempoFin = null;
    @DecimalMin("0.0")
    private Long duracionSegundos = 0L;
    private Boolean extendido = false;
}
