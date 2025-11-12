package microservicio.facturacion.dto.carga;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CargaResponse {
    private Long viajeId;
    private Long cuentaId;
    private Long montoTotal;
    private Double kmRecorridos;
    private LocalDateTime fechaCarga;
}
