package microservicio.facturacion.controller;


import lombok.RequiredArgsConstructor;
import microservicio.facturacion.dto.pago.PagoRequest;
import microservicio.facturacion.dto.pago.PagoResponse;
import microservicio.facturacion.service.PagoService;
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
@RequestMapping("pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponse>> todos() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<PagoResponse> porId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest req) {
        return ResponseEntity.ok(pagoService.save(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<PagoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PagoRequest req) {
        return ResponseEntity.ok(pagoService.update(id, req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINT ESPECIAL ---
    @GetMapping("total")
    public ResponseEntity<Double> totalFacturado(@RequestParam String desde, @RequestParam String hasta) {
        LocalDateTime d = LocalDateTime.parse(desde);
        LocalDateTime h = LocalDateTime.parse(hasta);
        return ResponseEntity.ok(pagoService.totalFacturadoEntre(d, h));
    }

    @GetMapping("porCuenta/{idCuenta}")
    public ResponseEntity<List<PagoResponse>> pagosPorCuenta(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(pagoService.pagosPorCuenta(idCuenta));
    }
}
