package microservicio.facturacion.dto.carga;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CargaRequest {
    @NotNull(message = "El ID del viaje no puede ser nulo")
    private Long viajeId;
    @NotNull(message = "El ID de la cuenta no puede ser nulo")
    private Long cuentaId;
    @NotNull(message = "El monto total no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto total no puede ser negativo")
    private Long montoTotal;
    @DecimalMin(value = "0.0", inclusive = true, message = "La carga normal no puede ser negativa")
    private Long cargaNormal;
    @DecimalMin(value = "0.0", inclusive = true, message = "La carga por pausa extendida no puede ser negativa")
    private Long cargaPausaExtendida = 0L;
    @DecimalMin(value = "0.0", inclusive = true, message = "La duración no puede ser negativa")
    private Long duracionMinutos = 0L;
    @DecimalMin(value = "0.0", inclusive = true, message = "La duración de la pausa no puede ser negativa")
    private Long duracionPausaMinutos = 0L;
    @DecimalMin(value = "0.0", inclusive = true, message = "Los kilómetros recorridos no pueden ser negativos")
    private Double kmRecorridos = 0.0;
    private LocalDateTime fechaCarga = LocalDateTime.now();
}
