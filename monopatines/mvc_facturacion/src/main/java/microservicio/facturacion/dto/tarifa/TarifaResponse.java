package microservicio.facturacion.dto.tarifa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaResponse {
    private Long id;
    private Double precioPorMinuto;
    private Double precioPorMinutoExtendido;
    private Double mensualidadPremium;
}
