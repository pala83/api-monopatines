package microservicio.mantenimiento.dto.registroMantenimiento;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroMantenimientoRequest {
	@NotNull
	private Long idMonopatin;
}
