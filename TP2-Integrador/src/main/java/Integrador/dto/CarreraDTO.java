package Integrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarreraDTO {
    private Integer id_carrera;
    private String carrera;
    private Integer duracion;
}
