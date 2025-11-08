package microservicio.monopatin.dto.monopatin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservicio.monopatin.entity.EstadoMonopatin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinResponse {
    private Long id;
    private String marca;
    private String codigoQR;
    private Double kmTotales;
    private EstadoMonopatin estado;
    private String ubicacionActual;
    private Long paradaActualId;
    private String paradaActualNombre;
}
