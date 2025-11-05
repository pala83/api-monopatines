package microservicio.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio.usuario.entity.Usuario;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
