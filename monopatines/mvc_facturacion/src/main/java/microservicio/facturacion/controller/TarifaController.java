package microservicio.facturacion.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservicio.facturacion.dto.tarifa.TarifaRequest;
import microservicio.facturacion.dto.tarifa.TarifaResponse;
import microservicio.facturacion.service.TarifaService;

@RestController
@RequestMapping("tarifas")
public class TarifaController {
    @Autowired
    private TarifaService tarifaService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<TarifaResponse> tarifas = tarifaService.findAll();
            return ResponseEntity.ok(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las tarifas");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe la Tarifa con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated TarifaRequest request){
        try{
            TarifaResponse tarifaResponse = tarifaService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(tarifaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("La tarifa no se pudo crear.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTarifa(@PathVariable Long id){
        try{
            this.tarifaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la tarifa con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la tarifa con id: " + id);
        }
    }

   @PutMapping("/editar/{id}")
    public ResponseEntity<?> editTarifa(@PathVariable Long id, @RequestBody @Validated TarifaRequest request){
        try {
            TarifaResponse response = this.tarifaService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tarifa con el ID: "+id);
        }
    }

    @GetMapping("/vigente")
    public ResponseEntity<?> getTarifaVigente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        try {
            TarifaResponse tarifa = tarifaService.findTarifaVigente(fecha);
            return ResponseEntity.ok(tarifa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró una tarifa vigente" + (fecha != null ? " para la fecha: " + fecha : " actual"));
        }
    }
}
