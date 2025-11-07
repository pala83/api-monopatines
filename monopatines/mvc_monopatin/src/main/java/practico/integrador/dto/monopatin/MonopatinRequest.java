package practico.integrador.dto.monopatin;


import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinRequest {
    @NotNull
    @Size(min = 3)
    private String codigoQR;

    @NotNull
    @DecimalMin("0.0")
    private Double kmTotales;

    @NotNull
    private String estado; // aceptar valores del enum

    private Long paradaActualId; // opcional
}

