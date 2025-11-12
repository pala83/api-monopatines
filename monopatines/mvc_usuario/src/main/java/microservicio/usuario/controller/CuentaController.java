package microservicio.usuario.controller;

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

import microservicio.usuario.dto.cuenta.CuentaRequest;
import microservicio.usuario.dto.cuenta.CuentaResponse;
import microservicio.usuario.service.CuentaService;

@RestController
@RequestMapping("cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<CuentaResponse> cuentas = cuentaService.findAll();
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las cuentas");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cuentaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe la Cuenta con el ID: "+id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody @Validated CuentaRequest request){
        try{
            CuentaResponse cuentaResponse = cuentaService.save(request);
            return ResponseEntity.status(HttpStatus.OK).body(cuentaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("La cuenta no se pudo crear.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        try{
            this.cuentaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la cuenta con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la cuenta con id: " + id);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editAccount(@PathVariable Long id, @RequestBody @Validated CuentaRequest request){
        try {
            CuentaResponse response = this.cuentaService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID: "+id);
        }
    }
}
