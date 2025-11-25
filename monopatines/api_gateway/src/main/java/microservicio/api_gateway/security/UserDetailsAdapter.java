package microservicio.api_gateway.security;

import microservicio.api_gateway.dto.client.UserDetailsRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Adaptador para convertir UserDetailsRecord a UserDetails de Spring Security.
 * Facilita la integración entre el modelo de usuario y Spring Security.
 */
@Component
public class UserDetailsAdapter {
    /**
     * Convierte un UserDetailsRecord a UserDetails de Spring Security.
     * @param userRecord el record de detalles de usuario
     * @return UserDetails objeto de Spring Security
     */
    public UserDetails toUserDetails(UserDetailsRecord userRecord) {
        List<GrantedAuthority> authorities = userRecord.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(
                userRecord.useremail(),
                "", // password no es necesario aquí
                authorities
        );
    }
}