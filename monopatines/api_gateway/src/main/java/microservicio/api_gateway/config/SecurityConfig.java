package microservicio.api_gateway.config;

import microservicio.api_gateway.security.jwt.JwtFilter;
import microservicio.api_gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // ========== ENDPOINTS PÚBLICOS ==========
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // Paradas públicas (solo consultas GET)
                        .requestMatchers(HttpMethod.GET, "/paradas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paradas/**").permitAll()

                        // Monopatines públicos (solo consultas GET específicas)
                        .requestMatchers(HttpMethod.GET, "/monopatines/disponibles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/monopatines/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/monopatines/{monopatinId}/esta-en-parada/{paradaId}").permitAll()

                        // ========== ENDPOINTS DE ADMINISTRADOR ==========
                        // Monopatines - CRUD completo (solo admin)
                        .requestMatchers(HttpMethod.GET, "/monopatines").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/monopatines").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/monopatines/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/monopatines/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/monopatines/{id}/estado").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/monopatines/{id}/ubicar-en-parada").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/monopatines/{id}/retirar-de-parada").hasAuthority("ADMIN")

                        // Paradas - Crear/Actualizar/Eliminar (solo admin)
                        .requestMatchers(HttpMethod.POST, "/paradas").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/paradas/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/paradas/{id}").hasAuthority("ADMIN")

                        // ========== ENDPOINTS DE USUARIO/PREMIUM ==========
                        .requestMatchers("/cuentas/**").hasAnyAuthority("USER", "PREMIUM", "ADMIN")
                        .requestMatchers("/viajes/**").hasAnyAuthority("USER", "PREMIUM", "ADMIN")
                        .requestMatchers("/pausas/**").hasAnyAuthority("USER", "PREMIUM", "ADMIN")
                        .requestMatchers("/cargas/**").hasAnyAuthority("USER", "PREMIUM", "ADMIN")
                        .requestMatchers("/subscripciones/**").hasAnyAuthority("USER", "PREMIUM", "ADMIN")

                        // ========== ENDPOINTS DE MANTENIMIENTO (ADMIN) ==========
                        .requestMatchers("/registroMantenimientos/**").hasAuthority("ADMIN")
                        .requestMatchers("/controlMantenimientos/**").hasAuthority("ADMIN")
                        .requestMatchers("/tarifas/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}