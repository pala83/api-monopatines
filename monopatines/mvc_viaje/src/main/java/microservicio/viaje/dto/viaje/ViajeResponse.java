package microservicio.viaje.dto.viaje;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservicio.viaje.entity.EstadoViaje;

@Data
@AllArgsConstructor
public class ViajeResponse {
    private Long usuarioId;
    private Long cuentaId;
    private Long monopatinId;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Long duracionSegundos;
    private double distanciaRecorrida;
    private EstadoViaje estado;
}
