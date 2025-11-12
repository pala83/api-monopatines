package microservicio.facturacion.dto.subscripcion;

import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.facturacion.entity.EstadoSubscripcion;

@Data
@RequiredArgsConstructor
public class SubscripcionRequest {
    @NotNull(message = "El id de la cuenta no puede ser nulo")
    private Long idCuenta;
    @NotNull(message = "El periodo de facturación no puede ser nulo")
    private YearMonth periodoFacturacion;
    @NotNull(message = "La fecha de pago no puede ser nula")
    private LocalDate fechaPago;
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto no puede ser negativo")
    private Long monto;
    @NotNull(message = "El estado no puede ser nulo")
    private EstadoSubscripcion estado;

}
