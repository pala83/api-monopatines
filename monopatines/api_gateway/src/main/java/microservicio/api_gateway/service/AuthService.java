package microservicio.api_gateway.service;

import microservicio.api_gateway.dto.client.LoginRequest;
import microservicio.api_gateway.security.UserDetailsAdapter;
import microservicio.api_gateway.security.jwt.JWTToken;
import microservicio.api_gateway.security.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("authService")
public class AuthService {

    @Autowired
    private UsuarioWebClientService usuarioWebClientService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserDetailsAdapter userDetailsAdapter;

    public AuthService(
            UsuarioWebClientService usuarioWebClientService,
            TokenProvider tokenProvider,
            UserDetailsAdapter userDetailsAdapter) {
        this.usuarioWebClientService = usuarioWebClientService;
        this.tokenProvider = tokenProvider;
        this.userDetailsAdapter = userDetailsAdapter;
    }

    public Mono<JWTToken> authenticate(LoginRequest login) {
        return usuarioWebClientService.validarCredenciales(login)
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