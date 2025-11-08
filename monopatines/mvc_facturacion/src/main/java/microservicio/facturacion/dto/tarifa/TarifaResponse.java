package microservicio.facturacion.dto.tarifa;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaResponse {
    private Long id;
    private Double precioPorMinuto;
    private Double precioPausaPorMinuto;
    private LocalDateTime fechaInicioVigencia;
}
