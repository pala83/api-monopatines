package microservicio.api_gateway.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
public class JwtFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String X_USER_ID_HEADER = "X-User-Id";
    public static final String X_USER_ROLES_HEADER = "X-User-Roles";

    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = resolveToken(request);
        try {
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Crear request wrapper para propagar headers a microservicios
                JwtHeaderRequestWrapper wrappedRequest = new JwtHeaderRequestWrapper(request);
                wrappedRequest.addHeader(X_USER_ID_HEADER, authentication.getName());
                wrappedRequest.addHeader(X_USER_ROLES_HEADER, getRolesString(authentication));

                filterChain.doFilter(wrappedRequest, response);
                return;
            }
        } catch (ExpiredJwtException e) {
            log.info("JWT token expired: {}", e.getMessage());
            handleJwtError(response, 498, "Token expired");
            return;
        } catch (Exception e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            handleJwtError(response, 401, "Invalid token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
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

    private void handleJwtError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new JwtErrorDTO(message).toJson());
    }

    @Getter
    private static class JwtErrorDTO {
        private final String message;
        private final String timestamp;

        public JwtErrorDTO(String message) {
            this.message = message;
            this.timestamp = LocalDateTime.now().toString();
        }

        public String toJson() {
            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (RuntimeException | JsonProcessingException ex) {
                return String.format("{\"message\": \"%s\", \"timestamp\": \"%s\"}", this.message, this.timestamp);
            }
        }
    }
}