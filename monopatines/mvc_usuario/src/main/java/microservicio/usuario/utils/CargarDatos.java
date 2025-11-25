package microservicio.usuario.utils;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import microservicio.usuario.repository.CuentaRepository;
import microservicio.usuario.repository.UsuarioRepository;
import microservicio.usuario.utils.dataManager.PopulatedCuentas;
import microservicio.usuario.utils.dataManager.PopulatedUsuarios;
import microservicio.usuario.utils.dataManager.PopulatedUsuariosCuentas;


@Component
public class CargarDatos implements CommandLineRunner{
    private static final String PATH_DATA = "data";
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private void cargarUsuariosCuentas() throws Exception {
        PopulatedUsuariosCuentas populator = new PopulatedUsuariosCuentas(
            Paths.get(PATH_DATA, "usuarios_cuentas.csv"),
            this.usuarioRepository,
            this.cuentaRepository
        );
        populator.poblar();
    }

    private void cargarUsuarios() throws Exception {
        PopulatedUsuarios populator = new PopulatedUsuarios(
            Paths.get(PATH_DATA, "usuarios.csv"),
            this.usuarioRepository, this.passwordEncoder
        );
        populator.poblar();
    }

    private void cargarCuentas() throws Exception {
        PopulatedCuentas populator = new PopulatedCuentas(
            Paths.get(PATH_DATA, "cuentas.csv"),
            this.cuentaRepository
        );
        populator.poblar();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Primero cargar cuentas, luego usuarios, y finalmente asociaciones ManyToMany
        this.cargarCuentas();
        this.cargarUsuarios();
        this.cargarUsuariosCuentas();
    }

}
