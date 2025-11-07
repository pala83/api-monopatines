package microservicio.usuario.dto.cuenta;

import java.sql.Timestamp;
import java.util.List;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.usuario.entity.Usuario;

@Data
@RequiredArgsConstructor
public class CuentaRequest {
    @Min(0)
    private Double saldo = 0.0;
    private Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
    private boolean activa = true;
    private List<Usuario> usuarios;
}
