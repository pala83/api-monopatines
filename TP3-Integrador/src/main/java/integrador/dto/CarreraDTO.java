package integrador.dto;

import integrador.entity.Carrera;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarreraDTO {
    private Long id;
    private String carrera;
    private Integer duracion;

    public CarreraDTO(Carrera c) {
        this.id = c.getId();
        this.carrera = c.getNombre();
        this.duracion = c.getDuracion();
    }
}
