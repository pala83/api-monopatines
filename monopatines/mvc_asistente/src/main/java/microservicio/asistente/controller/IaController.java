package microservicio.asistente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservicio.asistente.service.IaService;

@RestController
@RequestMapping("/api/ia")
public class IaController {

    @Autowired
    private IaService iaService;

    @PostMapping(value = "/prompt", produces = "application/json")
    public ResponseEntity<?> procesarPrompt(@RequestBody String prompt) {
        try {
            return iaService.procesarPrompt(prompt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el prompt: " + e.getMessage());
        }
    }
}
