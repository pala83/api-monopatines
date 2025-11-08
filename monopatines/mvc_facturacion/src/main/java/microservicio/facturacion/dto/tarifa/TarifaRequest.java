package microservicio.facturacion.dto.tarifa;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaRequest {
    @NotNull
    private Double precioPorMinuto;
    @NotNull
    private Double precioPausaPorMinuto;
    @NotNull
    private LocalDateTime fechaInicioVigencia;
}
