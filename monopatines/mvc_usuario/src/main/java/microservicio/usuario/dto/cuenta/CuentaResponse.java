package microservicio.usuario.dto.cuenta;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservicio.usuario.entity.TipoCuenta;

@Data
@AllArgsConstructor
public class CuentaResponse {
    private Long id;
    private Double saldo;
    private boolean activa;
    private Timestamp fechaCreacion;
    private TipoCuenta tipo;
}
