package microservicio.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservicio.usuario.dto.usuario.UsuarioRequest;
import microservicio.usuario.dto.usuario.UsuarioResponse;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

@Service("UsuarioService")
public class UsuarioService implements BaseService<UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return this.usuarioRepository
            .findAll()
            .stream()
            .map(this::castResponse)
            .toList();
    }

    @Override
    public UsuarioResponse findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
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
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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
