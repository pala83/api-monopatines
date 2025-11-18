package microservicio.usuario.controller;

import microservicio.usuario.dto.login.LoginRequest;
import microservicio.usuario.dto.login.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import microservicio.usuario.dto.usuario.UsuarioRequest;
import microservicio.usuario.dto.usuario.UsuarioResponse;
import microservicio.usuario.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<UsuarioResponse> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los usuarios");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No existe el Usuario con el ID: "+id);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            this.usuarioService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el usuario con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar el usuario con id: " + id);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try{
            UsuarioResponse usuarioResponse = usuarioService.save(usuarioRequest);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error al guardar el usuario");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated UsuarioRequest request){
        try {
            UsuarioResponse usuarioResponse = usuarioService.update(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el ID: "+id);
        }
    }
    @PostMapping("/validar")
    public ResponseEntity<Long> validarCredenciales(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(usuarioService.validarCredenciales(request.username(), request.password()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
