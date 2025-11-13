package microservicio.mantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import microservicio.mantenimiento.dto.feignClient.MonopatinResponse;

@FeignClient(name = "monopatin-service", url = "${monopatin.service.url}", path = "/monopatines")
public interface MonopatinClient {

    // Método para actualizar el estado de un monopatín
    @PatchMapping("/{id}/estado")
    void actualizarEstado(@PathVariable("id") Long id, @RequestBody String estado);
    
    // Método para verificar si un monopatín existe
    @GetMapping("/{id}")
    MonopatinResponse obtenerMonopatin(@PathVariable("id") Long id);
    
}
