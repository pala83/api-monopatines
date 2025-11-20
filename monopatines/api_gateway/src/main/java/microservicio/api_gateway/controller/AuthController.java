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

@RestController
@RequestMapping("authController")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("authenticate")
    public Mono<ResponseEntity<JWTToken>> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.authenticate(loginDTO).map(ResponseEntity::ok);
    }
}
