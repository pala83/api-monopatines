package microservicio.api_gateway.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "usuario-service", url = "${usuario.service.url:http://mvc-usuario:8082}")
public interface UsuarioServiceClient {

    @PostMapping("/usuarios/validar")
    Long validarCredenciales(@RequestBody LoginRequest request);

    @GetMapping("/usuarios/{id}/details")
    UserDetailsRecord getUserDetails(@PathVariable Long id);
}