package practico.integrador.dto.parada;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaRequest {
    @NotNull @Size(min = 2)
    private String nombre;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;
}
