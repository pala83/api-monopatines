package microservicio.monopatin.dto.monopatin;

import java.time.LocalDateTime;

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
    private Long usoTotalMinutos;
    private EstadoMonopatin estado;
    private LocalDateTime fechaUltimoMantenimiento;
    private String ubicacionActual;
    private Long paradaActualId;
    private String paradaActualNombre;
}
