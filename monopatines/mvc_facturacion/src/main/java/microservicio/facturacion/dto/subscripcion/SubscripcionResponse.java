package microservicio.facturacion.dto.subscripcion;

import java.time.LocalDate;
import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservicio.facturacion.entity.EstadoSubscripcion;
import microservicio.facturacion.entity.Vigencia;

@Data
@AllArgsConstructor
public class SubscripcionResponse {
    private Long idCuenta;
    private YearMonth periodoFacturacion;
    private LocalDate fechaPago;
    private Long monto;
    private EstadoSubscripcion estado;
    private Vigencia vigencia;
}
