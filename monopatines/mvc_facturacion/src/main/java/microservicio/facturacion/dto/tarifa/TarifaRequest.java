package microservicio.facturacion.dto.tarifa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.facturacion.entity.Vigencia;

@Data
@RequiredArgsConstructor
public class TarifaRequest {
    @DecimalMin("0.0")
    private Double precioPorMinuto;
    @DecimalMin("0.0")
    private Double precioPorMinutoExtendido;
    @DecimalMin("0.0")
    private Double mensualidadPremium;
    
    @NotNull(message = "La vigencia es obligatoria")
    @Valid
    private Vigencia vigencia;
}
