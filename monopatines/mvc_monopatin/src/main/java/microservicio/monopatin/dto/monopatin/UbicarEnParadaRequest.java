package microservicio.monopatin.dto.monopatin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UbicarEnParadaRequest {
    @NotNull(message = "El ID de la parada es obligatorio")
    private Long paradaId;
}
