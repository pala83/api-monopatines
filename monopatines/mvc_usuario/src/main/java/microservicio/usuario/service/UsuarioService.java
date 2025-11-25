package microservicio.usuario.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import microservicio.usuario.dto.login.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import microservicio.usuario.dto.usuario.UsuarioRequest;
import microservicio.usuario.dto.usuario.UsuarioResponse;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.repository.UsuarioRepository;
import microservicio.usuario.service.exception.NotFoundException;

@Service("UsuarioService")
@RequiredArgsConstructor
public class UsuarioService implements BaseService<UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

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
    @Transactional
    public Long validarCredenciales(String username, String password) {
        // Buscar usuario por email/useremail
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Validar contraseña
        if (passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario.getId(); // Devuelve ID si es válido
        }
        throw new RuntimeException("Credenciales inválidas");
    }
    @Transactional
    public UserDetailsResponse getUserDetails(Long id) {
        UsuarioResponse usuario = findById(id); // Tu método existente

        return new UserDetailsResponse(
                id,
                usuario.getEmail(),
                List.of(usuario.getRol().name())
        );
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
