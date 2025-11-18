package microservicio.api_gateway.controller;

import jakarta.validation.Valid;
import microservicio.api_gateway.dto.LoginRequest;
import microservicio.api_gateway.dto.UserDetailsRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        // 1. Validar credenciales y obtener ID
        Long userId = usuarioServiceClient.validarCredenciales(
                new LoginRequest(loginDTO.getUsername(), loginDTO.getPassword())
        );

        // 2. Obtener detalles del usuario
        UserDetailsRecord userRecord = usuarioServiceClient.getUserDetails(userId);

        // 3. Convertir a UserDetails de Spring Security
        UserDetails userDetails = userDetailsAdapter.toUserDetails(userRecord);

        // 4. Generar JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userRecord, null, userRecord.getAuthorities());

        String jwt = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new JWTToken(jwt));
    }
}
