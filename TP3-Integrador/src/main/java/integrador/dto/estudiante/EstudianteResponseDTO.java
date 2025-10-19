package integrador.dto.estudiante;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudianteResponseDTO {
    private Long dni;
    private String nombreCompleto;
    private String ciudad;
    private Integer lu;

}
