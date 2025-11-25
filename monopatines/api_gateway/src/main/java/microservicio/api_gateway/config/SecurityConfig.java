package microservicio.api_gateway.config;

import microservicio.api_gateway.security.jwt.JwtGatewayFilter;
import microservicio.api_gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // ========== ENDPOINTS PÚBLICOS ==========
                        .pathMatchers(HttpMethod.POST, "/authController/authenticate").permitAll()

                        // Paradas públicas (solo consultas GET)
                        .pathMatchers(HttpMethod.GET, "/paradas").permitAll()
                        .pathMatchers(HttpMethod.GET, "/paradas/**").permitAll()

                        // Monopatines públicos (solo consultas GET específicas)
                        .pathMatchers(HttpMethod.GET, "/monopatines/disponibles").permitAll()
                        .pathMatchers(HttpMethod.GET, "/monopatines/{id}").permitAll()
                        .pathMatchers(HttpMethod.GET, "/monopatines/{monopatinId}/esta-en-parada/{paradaId}").permitAll()

                        // ========== ENDPOINTS DE ADMINISTRADOR ==========
                        // Monopatines - CRUD completo (solo admin)
                        .pathMatchers(HttpMethod.GET, "/monopatines").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/monopatines").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/monopatines/{id}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/monopatines/{id}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/monopatines/{id}/estado").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/monopatines/{id}/ubicar-en-parada").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/monopatines/{id}/retirar-de-parada").hasRole("ADMIN")

                        // Paradas - Crear/Actualizar/Eliminar (solo admin)
                        .pathMatchers(HttpMethod.POST, "/paradas").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/paradas/{id}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/paradas/{id}").hasRole("ADMIN")

                        // ========== ENDPOINTS DE USUARIO/ADMIN ==========
                        .pathMatchers("/cuentas/**").hasAnyRole("USUARIO", "MANTENIMIENTO", "ADMIN")
                        .pathMatchers("/viajes/**").hasAnyRole("USUARIO", "MANTENIMIENTO", "ADMIN")
                        .pathMatchers("/pausas/**").hasAnyRole("USUARIO", "MANTENIMIENTO", "ADMIN")
                        .pathMatchers("/cargas/**").hasAnyRole("USUARIO", "MANTENIMIENTO", "ADMIN")
                        .pathMatchers("/subscripciones/**").hasAnyRole("USUARIO", "MANTENIMIENTO", "ADMIN")
                        .pathMatchers("/tarifas/**").hasAnyRole("MANTENIMIENTO", "ADMIN")

                        // ========== ENDPOINTS DE MANTENIMIENTO ==========
                        .pathMatchers("/registroMantenimientos/**").hasRole("MANTENIMIENTO")
                        .pathMatchers("/controlMantenimientos/**").hasRole("MANTENIMIENTO")

                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtGatewayFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}