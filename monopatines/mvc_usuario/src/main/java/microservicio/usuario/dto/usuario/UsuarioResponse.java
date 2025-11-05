package microservicio.usuario.dto.usuario;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import microservicio.usuario.entity.Cuenta;
import microservicio.usuario.entity.Rol;

@Data
@AllArgsConstructor
public class UsuarioResponse {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private List<Cuenta> cuentas;
    private Rol rol;

}
