package microservicio.facturacion.controller;

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

import microservicio.facturacion.dto.subscripcion.SubscripcionRequest;
import microservicio.facturacion.dto.subscripcion.SubscripcionResponse;
import microservicio.facturacion.service.SubscripcionService;

@RestController
@RequestMapping("subscripciones")
public class SubscripcionController {
    @Autowired
    private SubscripcionService subscripcionService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<SubscripcionResponse> subscripciones = subscripcionService.findAll();
            return ResponseEntity.ok(subscripciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las subscripciones");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(subscripcionService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe la Subscripcion con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated SubscripcionRequest request){
        try{
            SubscripcionResponse subscripcionResponse = subscripcionService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(subscripcionResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("La subscripcion no se pudo crear.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSubscripcion(@PathVariable Long id){
        try{
            this.subscripcionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la subscripcion con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la subscripcion con id: " + id);
        }
    }

   @PutMapping("/editar/{id}")
    public ResponseEntity<?> editSubscripcion(@PathVariable Long id, @RequestBody @Validated SubscripcionRequest request){
        try {
            SubscripcionResponse response = this.subscripcionService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la subscripcion con el ID: "+id);
        }
    }
}
