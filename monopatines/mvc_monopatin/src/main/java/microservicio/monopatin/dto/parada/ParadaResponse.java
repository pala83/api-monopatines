package microservicio.monopatin.dto.parada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservicio.monopatin.dto.monopatin.MonopatinResponse;
import microservicio.monopatin.entity.Monopatin;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaResponse {
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private List<MonopatinResponse> monopatines;
}
