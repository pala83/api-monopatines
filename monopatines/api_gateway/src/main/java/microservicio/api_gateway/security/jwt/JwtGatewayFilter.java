package microservicio.api_gateway.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
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

@Component
public class JwtGatewayFilter implements WebFilter {

    private final TokenProvider tokenProvider;

    public JwtGatewayFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

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

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getRolesString(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private Mono<Void> handleJwtError(ServerWebExchange exchange, int status, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.valueOf(status));
        return exchange.getResponse().setComplete();
    }
}