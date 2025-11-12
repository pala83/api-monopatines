package microservicio.usuario.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.usuario.entity.Rol;
import microservicio.usuario.entity.Usuario;
import microservicio.usuario.repository.UsuarioRepository;

public class PopulatedUsuarios extends Populated<Usuario> {
    private UsuarioRepository usuarioRepository;
    
    public PopulatedUsuarios(Path filePath, UsuarioRepository usuarioRepository) {
        super(filePath);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        List<Usuario> usuarios = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord record : records){
            try{ 
                Usuario u = new Usuario();
                u.setNombre(record.get("nombre"));
                u.setApellido(record.get("apellido"));
                u.setEmail(record.get("email"));
                u.setTelefono(record.get("telefono"));
                u.setPassword(record.get("password"));
                u.setRol(Rol.valueOf(record.get("rol")));
                usuarios.add(u);
            } catch (Exception e) {
                System.err.println("[PopulatedUsuarios.poblar] ERROR parsing registro de usuario: " + record.toString());
            }
        }
        try {
            if(!usuarios.isEmpty()){
                this.usuarioRepository.saveAll(usuarios);
                System.out.println("[PopulatedUsuarios.poblar] Se han poblado " + usuarios.size() + " usuarios.");
            } else {
                System.out.println("[PopulatedUsuarios.poblar] No hay usuarios para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedUsuarios.poblar] ERROR al guardar usuarios: " + e.getMessage());
        }
    }
}

