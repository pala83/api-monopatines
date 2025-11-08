package microservicio.facturacion.controller;

import lombok.RequiredArgsConstructor;
import microservicio.facturacion.dto.tarifa.TarifaRequest;
import microservicio.facturacion.dto.tarifa.TarifaResponse;
import microservicio.facturacion.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @GetMapping
    public ResponseEntity<List<TarifaResponse>> todos() {
        return ResponseEntity.ok(tarifaService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<TarifaResponse> porId(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<TarifaResponse> crear(@Valid @RequestBody TarifaRequest req) {
        return ResponseEntity.ok(tarifaService.save(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<TarifaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody TarifaRequest req) {
        return ResponseEntity.ok(tarifaService.update(id, req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        tarifaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINT ESPECIAL ---
    @GetMapping("vigente")
    public ResponseEntity<TarifaResponse> vigente(@RequestParam String fecha) {
        LocalDateTime f = LocalDateTime.parse(fecha);
        return ResponseEntity.ok(tarifaService.tarifaVigente(f));
    }
}
