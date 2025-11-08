package microservicio.monopatin.dto.monopatin;

import lombok.*;

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
