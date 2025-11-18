package microservicio.api_gateway.dto;

import java.util.List;

public record UserDetailsRecord(Long id, String username, List<String> authorities) {}