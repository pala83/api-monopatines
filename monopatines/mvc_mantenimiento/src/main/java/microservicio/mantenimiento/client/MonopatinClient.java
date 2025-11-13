package microservicio.mantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.lang.String;

// Cliente Feign para interactuar con el microservicio de monopatines
@FeignClient(name = "monopatin-service", url = "${monopatin.service.url}", path = "/monopatines")
public interface MonopatinClient {

    @PatchMapping("/{id}/estado")
    void actualizarEstado(
        @PathVariable("id") Long id,
        @RequestBody String estado);
}
