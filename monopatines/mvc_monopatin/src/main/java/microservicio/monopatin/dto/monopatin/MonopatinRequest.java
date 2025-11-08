package microservicio.monopatin.dto.monopatin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

