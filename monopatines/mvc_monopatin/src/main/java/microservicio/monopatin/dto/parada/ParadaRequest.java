package microservicio.monopatin.dto.parada;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaRequest {
    @NotNull
    @Size(min = 2)
    private String nombre;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;
}
