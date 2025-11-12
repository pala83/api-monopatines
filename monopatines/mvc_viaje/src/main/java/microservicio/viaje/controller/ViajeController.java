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

import microservicio.viaje.dto.viaje.ViajeRequest;
import microservicio.viaje.dto.viaje.ViajeResponse;
import microservicio.viaje.service.ViajeService;

@RestController
@RequestMapping("viajes")
public class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<ViajeResponse> viajes = viajeService.findAll();
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los viajes");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe el Viaje con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated ViajeRequest request){
        try{
            ViajeResponse viajeResponse = viajeService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(viajeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("El viaje no se pudo crear.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteViaje(@PathVariable Long id){
        try{
            this.viajeService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el viaje con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar el viaje con id: " + id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editViaje(@PathVariable Long id, @RequestBody @Validated ViajeRequest request){
        try {
            ViajeResponse response = this.viajeService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el viaje con el ID: "+id);
        }
    }
}
