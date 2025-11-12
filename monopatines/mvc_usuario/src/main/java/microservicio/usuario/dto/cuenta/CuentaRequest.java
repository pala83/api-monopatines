package microservicio.usuario.dto.cuenta;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.entity.TipoCuenta;

@Data
@RequiredArgsConstructor
public class CuentaRequest {
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    private Double saldo = 0.0;
    private Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
    private boolean activa = true;
    private List<Usuario> usuarios;
    private TipoCuenta tipo = TipoCuenta.BASICA;
}
