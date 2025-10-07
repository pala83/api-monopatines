package Integrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EstudianteEnCarreraXCiudadDTO {
    private Integer dni;
    private String nombre;
    private String apellido;
    private String carrera;
    private String ciudad;
}
