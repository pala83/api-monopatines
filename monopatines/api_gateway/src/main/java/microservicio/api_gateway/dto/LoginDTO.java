package microservicio.api_gateway.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull(message = "El email es requerido")
    @NotEmpty(message = "El email es requerido")
    private String useremail;

    @NotNull(message = "La contraseña es requerida")
    @NotEmpty(message = "La contraseña es requerida")
    private String password;

    public String toString(){
        return "Useremail: " + useremail + ", Password: [FORBIDDEN] ";
    }
}