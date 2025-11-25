package microservicio.api_gateway.dto.client;

import java.util.List;

public record UserDetailsRecord(Long id, String username, List<String> authorities) {}