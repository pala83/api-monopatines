package microservicio.api_gateway.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
/**
 * Proveedor de tokens JWT para crear y validar tokens.
 * Maneja la generación, parsing y validación de tokens JWT.
 */
@Component
public class TokenProvider {
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "authorities";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private int tokenValidityInSeconds;

    private final SecretKey key;
    private final JwtParser jwtParser;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") int tokenValidity) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parser().verifyWith(this.key).build();
        this.tokenValidityInSeconds = tokenValidity;
    }
    /**
     * Crea un nuevo token JWT para una autenticación dada.
     * @param authentication la autenticación del usuario
     * @return token JWT como string
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();
        Date validity = new Date(now + this.tokenValidityInSeconds * 1000L);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .expiration(validity)
                .issuedAt(new Date(now))
                .compact();
    }
    /**
     * Obtiene la autenticación a partir de un token JWT válido.
     * @param token el token JWT
     * @return Authentication objeto de autenticación Spring Security
     */
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(auth -> {
                    String a = auth.trim();
                    if (!a.startsWith("ROLE_")) {
                        a = "ROLE_" + a;
                    }
                    return new SimpleGrantedAuthority(a);
                })
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * Valida la firma y expiración de un token JWT.
     * @param authToken el token a validar
     * @return true si el token es válido, false si no
     */
    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> claims = jwtParser.parseSignedClaims(authToken);
            return !isTokenExpired(claims.getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.trace("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
    /**
     * Verifica si el token ha expirado comparando con la fecha actual.
     * @param claims los claims del token JWT
     * @return true si el token ha expirado, false si es válido
     */
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}