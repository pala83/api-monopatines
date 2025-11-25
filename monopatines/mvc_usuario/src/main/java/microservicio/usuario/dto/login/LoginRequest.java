package microservicio.usuario.dto.login;

public record LoginRequest(
        String useremail,
        String password
) {}