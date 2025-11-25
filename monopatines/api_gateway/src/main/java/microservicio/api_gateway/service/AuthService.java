package microservicio.api_gateway.service;

import microservicio.api_gateway.dto.LoginDTO;
import microservicio.api_gateway.dto.client.LoginRequest;
import microservicio.api_gateway.security.UserDetailsAdapter;
import microservicio.api_gateway.security.jwt.JWTToken;
import microservicio.api_gateway.security.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
/**
 * Servicio de autenticación para manejar el login y generación de JWT.
 * Coordina la validación de credenciales y creación de tokens.
 */
@Service("authService")
public class AuthService {

    private final microservicio.api_gateway.service.UsuarioWebClientService usuarioWebClientService;
    private final TokenProvider tokenProvider;
    private final UserDetailsAdapter userDetailsAdapter;

    public AuthService(
            UsuarioWebClientService usuarioWebClientService,
            TokenProvider tokenProvider,
            UserDetailsAdapter userDetailsAdapter) {
        this.usuarioWebClientService = usuarioWebClientService;
        this.tokenProvider = tokenProvider;
        this.userDetailsAdapter = userDetailsAdapter;
    }
    /**
     * Autentica un usuario y genera un token JWT.
     * @param loginDTO objeto con credenciales de login
     * @return Mono<JWTToken> con el token JWT generado
     */
    public Mono<JWTToken> authenticate(LoginDTO loginDTO) {
        LoginRequest loginRequest = new LoginRequest(loginDTO.getUseremail(), loginDTO.getPassword());

        return usuarioWebClientService.validarCredenciales(loginRequest)
                .flatMap(usuarioWebClientService::getUserDetails)
                .map(userRecord -> {
                    // Convertir a UserDetails de Spring Security
                    var userDetails = userDetailsAdapter.toUserDetails(userRecord);

                    // Generar JWT
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    String jwt = tokenProvider.createToken(authentication);
                    return new JWTToken(jwt);
                });
    }
}