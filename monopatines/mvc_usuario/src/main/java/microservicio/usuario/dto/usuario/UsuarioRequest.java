package microservicio.usuario.dto.usuario;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import microservicio.usuario.entity.Cuenta;
import microservicio.usuario.entity.Rol;

@Data
@RequiredArgsConstructor
public class UsuarioRequest {
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message = "El apellido no puede estar vacio")
    private String apellido;
    @NotNull(message = "El telefono no puede ser nulo")
    @Pattern(
      regexp = "^\\+?[1-9]\\d{1,14}$",
      message = "Teléfono inválido. Usar formato E.164 (ej: +5493412345678)"
    )
    private String telefono;
    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser valido")
    private String email;
    private List<Cuenta> cuentas;
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;
    @NotNull(message = "La contraseña no puede ser nula")
    @NotEmpty(message = "La contraseña no puede estar vacia")
    private String password;
}
