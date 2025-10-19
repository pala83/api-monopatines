package integrador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import integrador.dto.carrera.CarreraConEstudiantesDTO;
import integrador.services.CarreraService;

@RestController
@RequestMapping("carreras")
public class CarreraController {
    @Autowired
    private CarreraService carreraService;

    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @GetMapping("inscriptos")
    public ResponseEntity<?> getCarrerasConInscriptosOrdenado() {
        try {
            List<CarreraConEstudiantesDTO> result = carreraService.findCarrerasConInscriptosOrdenado();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
