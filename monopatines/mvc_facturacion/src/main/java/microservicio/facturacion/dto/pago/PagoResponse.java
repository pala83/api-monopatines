package microservicio.facturacion.dto.pago;

import lombok.*;
        import java.time.LocalDateTime;

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
