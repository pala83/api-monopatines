package microservicio.facturacion.dto.tarifa;



import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TarifaRequest {
    @DecimalMin("0.0")
    private Double precioPorMinuto;
    @DecimalMin("0.0")
    private Double precioPorMinutoExtendido;
    @DecimalMin("0.0")
    private Double mensualidadPremium;
}
