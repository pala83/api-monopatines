package microservicio.mantenimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.RequiredArgsConstructor;
import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoRequest;
import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoResponse;
import microservicio.mantenimiento.service.ControlMantenimientoService;

@RestController
@RequestMapping("controles")
@RequiredArgsConstructor
public class ControlMantenimientoController {
    @Autowired
    private final ControlMantenimientoService controlMantenimientoService;

    @GetMapping
    public ResponseEntity<List<ControlMantenimientoResponse>> getAll() {
        return ResponseEntity.ok(controlMantenimientoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ControlMantenimientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(controlMantenimientoService.findById(id));
    }

    @GetMapping("/activo/{activo}")
    public ResponseEntity<List<ControlMantenimientoResponse>> getByActivo(@PathVariable Boolean activo) {
        return ResponseEntity.ok(controlMantenimientoService.findByActivo(activo));
    }

    @PostMapping
    public ResponseEntity<ControlMantenimientoResponse> crear(@Validated @RequestBody ControlMantenimientoRequest req) {
        return ResponseEntity.ok(controlMantenimientoService.save(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<ControlMantenimientoResponse> actualizar(
            @PathVariable Long id,
            @Validated @RequestBody ControlMantenimientoRequest req) {
        return ResponseEntity.ok(controlMantenimientoService.update(id, req));
    }

    @PostMapping("{id}/finalizar/{idMonopatin}")
    public ResponseEntity<ControlMantenimientoResponse> finalizarMantenimiento(
            @PathVariable Long id,
            @PathVariable Long idMonopatin) {
        return ResponseEntity.ok(controlMantenimientoService.finalizarMantenimiento(id, idMonopatin));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        controlMantenimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}