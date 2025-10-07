package Integrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarrerasConInscriptosDTO {
    private String nombreCarrera;
    private Long cantidadInscriptos;
}
