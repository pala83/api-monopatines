package microservicio.monopatin.dto.monopatin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinResponse {
    private Long id;
    private String codigoQR;
    private Double kmTotales;
    private String estado;
    private Long paradaActualId;
    private String paradaActualNombre;
}
