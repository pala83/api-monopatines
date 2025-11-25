package microservicio.api_gateway.service;

import microservicio.api_gateway.dto.client.LoginRequest;
import microservicio.api_gateway.dto.client.UserDetailsRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UsuarioWebClientService {

    @Autowired
    private WebClient webClient;

    public UsuarioWebClientService(
            WebClient.Builder webClientBuilder,
            @Value("${usuario.service.url}") String usuarioServiceUrl) {
        // Validación adicional
        if (usuarioServiceUrl == null || usuarioServiceUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del servicio de usuario no puede estar vacía");
        }
        this.webClient = webClientBuilder.baseUrl(usuarioServiceUrl).build();
    }

    public Mono<Long> validarCredenciales(LoginRequest request) {
        return webClient.post()
                .uri("/usuarios/validar")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Long.class);
    }

    public Mono<UserDetailsRecord> getUserDetails(Long userId) {
        return webClient.get()
                .uri("/usuarios/{id}/details", userId)
                .retrieve()
                .bodyToMono(UserDetailsRecord.class);
    }
}