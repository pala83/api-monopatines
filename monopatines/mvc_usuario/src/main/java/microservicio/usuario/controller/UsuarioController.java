package microservicio.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import microservicio.usuario.dto.usuario.UsuarioRequest;
import microservicio.usuario.dto.usuario.UsuarioResponse;
import microservicio.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try{
            UsuarioResponse usuarioResponse = usuarioService.save(usuarioRequest);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el usuario");
        }
    }
    

}
