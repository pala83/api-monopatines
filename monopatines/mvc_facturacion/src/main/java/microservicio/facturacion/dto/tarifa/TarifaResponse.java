package microservicio.facturacion.dto.tarifa;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaResponse {
    private Long id;
    private Double precioPorMinuto;
    private Double precioPausaPorMinuto;
    private LocalDateTime fechaInicioVigencia;
}
