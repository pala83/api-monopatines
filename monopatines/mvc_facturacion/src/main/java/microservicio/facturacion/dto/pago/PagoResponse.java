package microservicio.facturacion.dto.pago;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponse {
    private Long id;
    private Long idCuenta;
    private Double monto;
    private LocalDateTime fechaPago;
    private String medio;
}
