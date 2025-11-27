package microservicio.api_gateway.dto.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "El usuario es requerido")
    @NotEmpty(message = "El usuario es requerido")
    @JsonAlias({"useremail", "username"})
    private String username;

    @NotNull(message = "La contraseña es requerida")
    @NotEmpty(message = "La contraseña es requerida")
    private String password;

    public String toString(){
        return "Username: " + username + ", Password: [FORBIDDEN] ";
    }
}