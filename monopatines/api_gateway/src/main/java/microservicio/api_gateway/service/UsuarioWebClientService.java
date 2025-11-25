package microservicio.api_gateway.service;

import microservicio.api_gateway.dto.client.LoginRequest;
import microservicio.api_gateway.dto.client.UserDetailsRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
        return webClient.post()
                .uri("/usuarios/validar")
                .bodyValue(request)
                .retrieve()
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