package integrador.dto;

import integrador.entity.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EstudianteDTO {
    private Long dni;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String genero;
    private String ciudad;
    private Integer lu;

    public EstudianteDTO(Estudiante e) {
        this.dni = e.getDni();
        this.nombre = e.getNombre();
        this.apellido = e.getApellido();
        this.edad = e.getEdad();
        this.genero = e.getGenero();
        this.ciudad = e.getCiudad();
        this.lu = e.getLu();
    }
}