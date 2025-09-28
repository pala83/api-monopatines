package ej1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaDTO {
    private String nombre;
    private Integer edad;
    private String ciudad;
    private String calle;
    private Integer numero;
}
