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

import microservicio.facturacion.dto.carga.CargaRequest;
import microservicio.facturacion.dto.carga.CargaResponse;
import microservicio.facturacion.service.CargaService;

@RestController
@RequestMapping("cargas")
public class CargaController {
    @Autowired
    private CargaService cargaService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<CargaResponse> cargas = cargaService.findAll();
            return ResponseEntity.ok(cargas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las cargas");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cargaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe la Carga con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated CargaRequest request){
        try{
            CargaResponse cargaResponse = cargaService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(cargaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("La carga no se pudo crear.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCarga(@PathVariable Long id){
        try{
            this.cargaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la carga con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la carga con id: " + id);
        }
    }

   @PutMapping("/editar/{id}")
    public ResponseEntity<?> editCarga(@PathVariable Long id, @RequestBody @Validated CargaRequest request){
        try {
            CargaResponse response = this.cargaService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la carga con el ID: "+id);
        }
    }

}
