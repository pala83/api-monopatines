package practico.integrador.controller;


import lombok.RequiredArgsConstructor;
import practico.integrador.dto.monopatin.*;
import practico.integrador.service.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("monopatines")
@RequiredArgsConstructor
public class MonopatinController {

    private final MonopatinService monopatinService;

    @GetMapping
    public ResponseEntity<List<MonopatinResponse>> getAll() {
        return ResponseEntity.ok(monopatinService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<MonopatinResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(monopatinService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<MonopatinResponse> create(@Validated @RequestBody MonopatinRequest req) {
        return ResponseEntity.ok(monopatinService.save(req));
    }

    @PutMapping("{id}")
    public ResponseEntity<MonopatinResponse> update(@PathVariable Long id, @Validated @RequestBody MonopatinRequest req) {
        return ResponseEntity.ok(monopatinService.update(id, req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        monopatinService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("disponibles")
    public ResponseEntity<List<MonopatinResponse>> disponibles() {
        return ResponseEntity.ok(monopatinService.listarDisponibles());
    }

    // comentado porque rompetodo la query hay que arreglarla
//    @GetMapping("cercanos")
//    public ResponseEntity<List<MonopatinResponse>> cercanos(
//            @RequestParam Double lat,
//            @RequestParam Double lon,
//            @RequestParam(defaultValue = "10") Integer limit
//    ) {
//        return ResponseEntity.ok(monopatinService.listarPorCercania(lat, lon, limit));
//    }
}
