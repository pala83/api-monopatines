package microservicio.mantenimiento.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoRequest;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoResponse;
import microservicio.mantenimiento.service.RegistroMantenimientoService;

@RestController
@RequestMapping("registroMantenimientos")
@RequiredArgsConstructor
public class RegistroMantenimientoController {
    @Autowired
    private final RegistroMantenimientoService registroMantenimientoService;

    // Obtener todos los registros de mantenimiento
    @GetMapping("")
    public ResponseEntity<List<RegistroMantenimientoResponse>> getAll() {
        return ResponseEntity.ok(registroMantenimientoService.findAll());
    }

    // Obtener un registro de mantenimiento por ID
    @GetMapping("{id}")
    public ResponseEntity<RegistroMantenimientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(registroMantenimientoService.findById(id));
    }

    // Obtener todos los registros de mantenimiento de un monopatín específico
    @GetMapping("/monopatin/{idMonopatin}")
    public ResponseEntity<List<RegistroMantenimientoResponse>> getByMonopatin(@PathVariable Long idMonopatin) {
        return ResponseEntity.ok(registroMantenimientoService.findByIdMonopatin(idMonopatin));
    }

    // Obtener los registros de mantenimiento activos de un monopatín
    @GetMapping("/monopatin/{idMonopatin}/activos")
    public ResponseEntity<List<RegistroMantenimientoResponse>> getMantenimientosActivos(@PathVariable Long idMonopatin) {
        return ResponseEntity.ok(registroMantenimientoService.findMantenimientosActivos(idMonopatin));
    }

    // Registrar un monopatín en mantenimiento y marcarlo NO disponible
    @PostMapping
    public ResponseEntity<RegistroMantenimientoResponse> registrar(@Validated @RequestBody RegistroMantenimientoRequest req) {
        return ResponseEntity.ok(registroMantenimientoService.save(req));
    }

    // Finalizar un registro de mantenimiento y marcar el monopatín como DISPONIBLE
    @RequestMapping(value = "/{id}/finalizar", method = {RequestMethod.PATCH, RequestMethod.POST})
    //pongo esto asi para que ande la basura del swagger u openapi no soporta el
    // patch para editar el estado de monopatin cosa que si anda en postman, me canse de renegar asique lo dejo asi
    public ResponseEntity<RegistroMantenimientoResponse> finalizarMantenimiento(@PathVariable Long id) {
        return ResponseEntity.ok(registroMantenimientoService.finalizarMantenimiento(id));
    }
}
