package microservicio.api_gateway.controller;

import jakarta.validation.Valid;
import microservicio.api_gateway.dto.LoginDTO;
import microservicio.api_gateway.security.jwt.JWTToken;
import microservicio.api_gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para manejar endpoints de autenticación.
 * Expone operaciones de login y generación de tokens JWT.
 */
@RestController
@RequestMapping("authController")
public class AuthController {

    /**
     * Servicio de autenticación inyectado para manejar la lógica de login.
     */
    @Autowired
    private AuthService authService;

    /**
     * Endpoint para autenticar usuarios y generar token JWT.
     * @param loginDTO objeto con las credenciales de usuario (useremail y password)
     * @return Mono<ResponseEntity<JWTToken>> con el token JWT en caso de autenticación exitosa
     */
    @PostMapping("authenticate")
    public Mono<ResponseEntity<JWTToken>> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.authenticate(loginDTO).map(ResponseEntity::ok);
    }
}