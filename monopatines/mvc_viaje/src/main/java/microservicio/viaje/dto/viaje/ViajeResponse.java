package microservicio.viaje.dto.viaje;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservicio.viaje.entity.EstadoViaje;
import microservicio.viaje.entity.Ubicacion;

@Data
@AllArgsConstructor
public class ViajeResponse {
    private Long usuarioId;
    private Long cuentaId;
    private Long monopatinId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long duracionSegundos;
    private double distanciaRecorrida;
    private EstadoViaje estado;
    private Ubicacion ubicacionInicio;
    private Ubicacion ubicacionFin;
}
