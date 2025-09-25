package ej8.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DireccionDTO {

    private String ciudad;
    private String calle;
    private Integer numero;
    private Integer codigoPostal;
}
