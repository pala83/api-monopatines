package microservicio.mantenimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoRequest;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoResponse;
import microservicio.mantenimiento.service.RegistroMantenimientoService;

@RestController
@RequestMapping("registros")
@RequiredArgsConstructor
public class RegistroMantenimientoController {
    @Autowired
    private final RegistroMantenimientoService registroMantenimientoService;

    @GetMapping
    public ResponseEntity<List<RegistroMantenimientoResponse>> getAll() {
        return ResponseEntity.ok(registroMantenimientoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RegistroMantenimientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(registroMantenimientoService.findById(id));
    }

    // Registrar un monopatín en mantenimiento y marcarlo NO disponible
    @PostMapping
    public ResponseEntity<RegistroMantenimientoResponse> registrar(@Validated @RequestBody RegistroMantenimientoRequest req) {
        return ResponseEntity.ok(registroMantenimientoService.save(req));
    }
}
