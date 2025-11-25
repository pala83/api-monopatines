package microservicio.usuario.dto.login;

import java.util.List;

public record UserDetailsResponse(
        Long id,
        String useremail,
        List<String> authorities
) {}