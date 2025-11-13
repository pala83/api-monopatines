package microservicio.mantenimiento.dto.registroMantenimiento;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistroMantenimientoResponse {
	private Long id;
	private Long idMonopatin;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
}
