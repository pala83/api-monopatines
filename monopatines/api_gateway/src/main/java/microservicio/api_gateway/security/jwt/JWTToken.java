package microservicio.api_gateway.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTToken {
    @JsonProperty("id_token")
    private String idToken;
}
