package microservicio.monopatin.controller;



import lombok.RequiredArgsConstructor;
import microservicio.monopatin.dto.parada.ParadaRequest;
import microservicio.monopatin.dto.parada.ParadaResponse;
import microservicio.monopatin.service.ParadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("monopatin/{id}")
    public ResponseEntity<ParadaResponse> getByMonopatin(@PathVariable Long id) {
        return ResponseEntity.ok(paradaService.findMonopatinById(id));
    }
    @PostMapping("")
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
