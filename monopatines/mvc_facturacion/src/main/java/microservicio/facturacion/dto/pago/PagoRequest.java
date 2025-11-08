package microservicio.facturacion.dto.pago;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {
    @NotNull
    private Long idCuenta;
    @NotNull
    private Double monto;
    @NotNull
    private LocalDateTime fechaPago;
    @NotNull
    private String medio;
}
