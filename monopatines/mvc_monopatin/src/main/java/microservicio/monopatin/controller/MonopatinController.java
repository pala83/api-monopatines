package microservicio.monopatin.controller;


import lombok.RequiredArgsConstructor;
import microservicio.monopatin.dto.monopatin.MonopatinRequest;
import microservicio.monopatin.dto.monopatin.MonopatinResponse;
import microservicio.monopatin.dto.monopatin.UbicarEnParadaRequest;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.service.MonopatinService;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("monopatines")
@RequiredArgsConstructor
public class MonopatinController {
    @Autowired
    private MonopatinService monopatinService;  

    @GetMapping
    public ResponseEntity<List<MonopatinResponse>> getAll() {
        return ResponseEntity.ok(monopatinService.findAll());
    }

    // Obtener un monopatín por ID
    @GetMapping("{id}")
    public ResponseEntity<MonopatinResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(monopatinService.findById(id));
    }

    // Crear un nuevo monopatín
    @PostMapping("")
    public ResponseEntity<MonopatinResponse> create(@Validated @RequestBody MonopatinRequest req) {
        return ResponseEntity.ok(monopatinService.save(req));
    }

    // Actualizar un monopatín por ID
    @PutMapping("{id}")
    public ResponseEntity<MonopatinResponse> update(@PathVariable Long id, @Validated @RequestBody MonopatinRequest req) {
        return ResponseEntity.ok(monopatinService.update(id, req));
    }

    // Eliminar un monopatín por ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        monopatinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Listar todos los monopatines disponibles
    @GetMapping("disponibles")
    public ResponseEntity<List<MonopatinResponse>> disponibles() {
        return ResponseEntity.ok(monopatinService.listarDisponibles());
    }

    // Endpoint para actualizar solo el estado del monopatín
    @PatchMapping("{id}/estado")
    public ResponseEntity<MonopatinResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EstadoMonopatin estado
    ) {
        return ResponseEntity.ok(monopatinService.actualizarEstado(id, estado));
    }

    // Ubicar un monopatín en una parada específica
    @PatchMapping("{id}/ubicar-en-parada")
    public ResponseEntity<MonopatinResponse> ubicarEnParada(
            @PathVariable Long id,
            @Validated @RequestBody UbicarEnParadaRequest request
    ) {
        return ResponseEntity.ok(monopatinService.ubicarEnParada(id, request.getParadaId()));
    }

    // Retirar un monopatín de su parada actual
    @PatchMapping("{id}/retirar-de-parada")
    public ResponseEntity<MonopatinResponse> retirarDeParada(@PathVariable Long id) {
        return ResponseEntity.ok(monopatinService.retirarDeParada(id));
    }

    // Verificar si un monopatín está en una parada específica
    @GetMapping("{monopatinId}/esta-en-parada/{paradaId}")
    public ResponseEntity<Boolean> verificarMonopatinEnParada(
            @PathVariable Long monopatinId,
            @PathVariable Long paradaId
    ) {
        boolean estaEnParada = monopatinService.verificarMonopatinEnParada(paradaId, monopatinId);
        return ResponseEntity.ok(estaEnParada);
    }
}
