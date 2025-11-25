package microservicio.api_gateway.security;

import microservicio.api_gateway.dto.client.UserDetailsRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsAdapter {

    public UserDetails toUserDetails(UserDetailsRecord userRecord) {
        List<GrantedAuthority> authorities = userRecord.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(
                userRecord.username(),
                "", // password no es necesario aquí
                authorities
        );
    }
}