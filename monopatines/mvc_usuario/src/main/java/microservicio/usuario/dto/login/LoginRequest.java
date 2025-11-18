package microservicio.usuario.dto.login;

public record LoginRequest(
        String username,
        String password
) {}