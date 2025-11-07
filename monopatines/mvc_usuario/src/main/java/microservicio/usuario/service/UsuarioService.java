package microservicio.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import microservicio.usuario.dto.usuario.UsuarioRequest;
import microservicio.usuario.dto.usuario.UsuarioResponse;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.repository.UsuarioRepository;
import microservicio.usuario.service.exception.NotFoundException;

@Service("UsuarioService")
public class UsuarioService implements BaseService<UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<UsuarioResponse> findAll() {
        return this.usuarioRepository
            .findAll()
            .stream()
            .map(this::castResponse)
            .toList();
    }

    @Override
    @Transactional
    public UsuarioResponse findById(Long id) {
        return this.usuarioRepository.findById( id )
                .map( this::castResponse )
                .orElseThrow( () -> new NotFoundException("usuario", id));
    }

    @Override
    @Transactional
    public UsuarioResponse save(UsuarioRequest entity) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(entity.getNombre());
        nuevo.setApellido(entity.getApellido());
        nuevo.setEmail(entity.getEmail());
        nuevo.setTelefono(entity.getTelefono());
        nuevo.setPassword(entity.getPassword());
        nuevo.setCuentas(entity.getCuentas());
        nuevo.setRol(entity.getRol());
        return this.castResponse(this.usuarioRepository.save(nuevo));
    }

    @Override
    @Transactional
    public void delete(Long id) {        
        this.usuarioRepository.delete(this.usuarioRepository.findById(id).orElseThrow(
            () -> new NotFoundException("No se pudo eliminar usuario con ID:" + id)));
    }

    @Override
    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario u = this.usuarioRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No se encontro usuario con ID: " + id));
        u.setNombre(request.getNombre());
        u.setApellido(request.getApellido());
        u.setEmail(request.getEmail());
        u.setTelefono(request.getTelefono());
        u.setPassword(request.getPassword());
        u.setRol(request.getRol());
        u.setCuentas(request.getCuentas());
        return castResponse(this.usuarioRepository.save(u));
    }

    private UsuarioResponse castResponse(Usuario usuario){
        return new UsuarioResponse(
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getTelefono(),
            usuario.getEmail(),
            usuario.getCuentas(),
            usuario.getRol()
            );
    }
}
