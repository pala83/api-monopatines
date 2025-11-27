package microservicio.api_gateway.service;

import microservicio.api_gateway.dto.client.LoginRequest;
import microservicio.api_gateway.dto.client.UserDetailsRecord;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import java.util.Objects;
import java.util.Map;
/**
 * Cliente WebClient para comunicarse con el microservicio de usuarios.
 * Realiza llamadas HTTP para validar credenciales y obtener detalles de usuario.
 */
@Service
public class UsuarioWebClientService {
    private final WebClient webClient;

    public UsuarioWebClientService(
            WebClient.Builder webClientBuilder,
            @Value("${usuario.service.url}") String usuarioServiceUrl) {
        // Validación adicional
        if (usuarioServiceUrl == null || usuarioServiceUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del servicio de usuario no puede estar vacía");
        }
        this.webClient = webClientBuilder.baseUrl(usuarioServiceUrl).build();
    }
    /**
     * Valida credenciales de usuario con el microservicio de usuarios.
     * @param request objeto LoginRequest con credenciales
     * @return Mono<Long> con el ID de usuario si las credenciales son válidas
     */
    public Mono<Long> validarCredenciales(LoginRequest request) {
        Objects.requireNonNull(request, "LoginRequest no puede ser nulo");
    // El microservicio de usuarios espera 'useremail' y 'password'.
    Map<String, Object> body = Map.of(
        "useremail", request.getUsername(),
        "password", request.getPassword()
    );

    return webClient.post()
        .uri("/usuarios/validar")
        .bodyValue(body)
        .retrieve()
        .onStatus(status -> status.value() == 401,
            resp -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas")))
        .bodyToMono(Long.class);
    }
    /**
     * Obtiene detalles completos de un usuario por su ID.
     * @param userId el ID del usuario
     * @return Mono<UserDetailsRecord> con los detalles del usuario
     */
    public Mono<UserDetailsRecord> getUserDetails(Long userId) {
        return webClient.get()
                .uri("/usuarios/{id}/details", userId)
                .retrieve()
                .bodyToMono(UserDetailsRecord.class);
    }
}