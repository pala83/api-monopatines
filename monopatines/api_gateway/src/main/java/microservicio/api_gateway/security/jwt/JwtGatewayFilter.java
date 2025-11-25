package microservicio.api_gateway.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
/**
 * Filtro de seguridad para validar JWT en el Gateway.
 * Extrae tokens de las solicitudes, los valida y propaga información de autenticación.
 */
@Component
public class JwtGatewayFilter implements WebFilter {

    private final TokenProvider tokenProvider;

    public JwtGatewayFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    /**
     * Filtra las solicitudes HTTP para validar tokens JWT.
     * @param exchange el intercambio de la solicitud web
     * @param chain la cadena de filtros
     * @return Mono<Void> para continuar o rechazar la solicitud
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());

        if (!StringUtils.hasText(token)) {
            return chain.filter(exchange);
        }

        try {
            if (tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                // Establecer SecurityContext reactivo
                SecurityContext securityContext = new SecurityContextImpl(authentication);

                // Agregar headers para microservicios
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                        .header("Authorization", "Bearer " + token)
                        .header("X-User-Id", authentication.getName())
                        .header("X-User-Roles", getRolesString(authentication))
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build())
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
            }
        } catch (ExpiredJwtException e) {
            return handleJwtError(exchange, 498, "Token expired");
        } catch (Exception e) {
            return handleJwtError(exchange, 401, "Invalid token");
        }

        return chain.filter(exchange);
    }
    /**
     * Extrae el token JWT del header Authorization de la solicitud.
     * @param request la solicitud HTTP
     * @return el token JWT o null si no se encuentra
     */
    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (bearerToken == null) {
            bearerToken = request.getHeaders().getFirst("Authorization:");
        }
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            System.out.println("Token extraído: " + token);
            return token;
        }
        System.out.println("No se pudo resolver el token (faltante o mal formado)");
        return null;
    }
    /**
     * Convierte las autoridades de autenticación a string de roles separados por comas.
     * @param authentication el objeto de autenticación
     * @return string de roles separados por comas
     */
    private String getRolesString(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
    /**
     * Maneja errores de JWT devolviendo respuestas HTTP con código y mensaje de error.
     * @param exchange el intercambio de la solicitud web
     * @param status código de estado HTTP
     * @param message mensaje de error
     * @return Mono<Void> con la respuesta de error
     */
    private Mono<Void> handleJwtError(ServerWebExchange exchange, int status, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.valueOf(status));
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        String errorBody = "{\"error\": \"" + message + "\", \"status\": " + status + "}";
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorBody.getBytes());

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}