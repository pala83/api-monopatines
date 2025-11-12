package microservicio.viaje.dto.pausa;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PausaResponse {
    private Long viajeId;
    private LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFin;
}
