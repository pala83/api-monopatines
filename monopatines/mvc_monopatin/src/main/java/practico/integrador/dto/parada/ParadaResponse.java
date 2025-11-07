package practico.integrador.dto.parada;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaResponse {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
}
