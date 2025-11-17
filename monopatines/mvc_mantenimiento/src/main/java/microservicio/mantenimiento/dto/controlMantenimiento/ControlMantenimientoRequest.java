package microservicio.mantenimiento.dto.controlMantenimiento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlMantenimientoRequest {
    private Double kilometraje;
    private Long usoMinutos;
    private Boolean activo;
}