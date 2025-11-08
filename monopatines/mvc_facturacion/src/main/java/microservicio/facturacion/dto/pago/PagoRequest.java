package microservicio.facturacion.dto.pago;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
