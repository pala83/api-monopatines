package microservicio.facturacion.dto.tarifa;



import lombok.*;
import jakarta.validation.constraints.NotNull;
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
