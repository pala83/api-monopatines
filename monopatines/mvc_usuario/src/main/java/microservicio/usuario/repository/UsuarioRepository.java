package microservicio.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.usuario.entity.Usuario;

import java.util.Optional;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByEmail(String email);
}
