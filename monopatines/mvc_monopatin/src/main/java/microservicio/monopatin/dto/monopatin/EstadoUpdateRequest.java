package microservicio.monopatin.dto.monopatin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservicio.monopatin.entity.EstadoMonopatin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoUpdateRequest {
    private EstadoMonopatin estado;
}
