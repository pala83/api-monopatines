package microservicio.viaje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import microservicio.viaje.dto.pausa.PausaRequest;
import microservicio.viaje.dto.pausa.PausaResponse;
import microservicio.viaje.service.PausaService;

@RestController
@RequestMapping("pausas")
public class PausaController {
    @Autowired
    private PausaService pausaService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<PausaResponse> cuentas = pausaService.findAll();
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las pausas");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pausaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe la Pausa con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated PausaRequest request){
        try{
            PausaResponse pausaResponse = pausaService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(pausaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("La cuenta no se pudo crear.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePausa(@PathVariable Long id){
        try{
            this.pausaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la pausa con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la pausa con id: " + id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editPausa(@PathVariable Long id, @RequestBody @Validated PausaRequest request){
        try {
            PausaResponse response = this.pausaService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la pausa con el ID: "+id);
        }
    }

}
