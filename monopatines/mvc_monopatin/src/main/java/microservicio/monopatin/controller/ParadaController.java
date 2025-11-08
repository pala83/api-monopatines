package microservicio.monopatin.controller;



import lombok.RequiredArgsConstructor;
import microservicio.monopatin.dto.parada.ParadaRequest;
import microservicio.monopatin.dto.parada.ParadaResponse;
import microservicio.monopatin.dto.parada.*;
import microservicio.monopatin.service.ParadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("paradas")
@RequiredArgsConstructor
public class ParadaController {

    private final ParadaService paradaService;

    @GetMapping
    public ResponseEntity<List<ParadaResponse>> getAll() {
        return ResponseEntity.ok(paradaService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ParadaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paradaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParadaResponse> create(@Validated @RequestBody ParadaRequest req) {
        return ResponseEntity.ok(paradaService.save(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<ParadaResponse> update(@PathVariable Long id, @Validated @RequestBody ParadaRequest req) {
        return ResponseEntity.ok(paradaService.update(id, req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paradaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
