package microservicio.mantenimiento.dto.controlMantenimiento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlMantenimientoResponse {
    private Long id;
    private Double kilometraje;
    private Long usoMinutos;
    private Boolean activo;
    private LocalDateTime ultimoMantenimiento;
}