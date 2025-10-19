package integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import integrador.dto.inscripcion.InscripcionRequestDTO;
import integrador.dto.inscripcion.InscripcionResponseDTO;
import integrador.services.InscripcionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("inscripciones")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;

    // b) matricular un estudiante en una carrera
    @PostMapping("")
    public ResponseEntity<?> saveInscripcion(@Valid @RequestBody InscripcionRequestDTO i){
        try {
            InscripcionResponseDTO inscripcionGuardada = this.inscripcionService.save(i);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionGuardada);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // h) generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @GetMapping("reporte")
    public ResponseEntity<?> generarReporte(){
        try {
            return ResponseEntity.ok(this.inscripcionService.generarReporte());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getInscripciones(){
        try {
            return ResponseEntity.ok(this.inscripcionService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getInscripcionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.inscripcionService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
