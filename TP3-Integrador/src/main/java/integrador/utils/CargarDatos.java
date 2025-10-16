package integrador.utils;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import integrador.repository.EstudianteRepository;
import integrador.utils.dataManager.PopulatedEstudiantes;

@Component
public class CargarDatos implements CommandLineRunner {
    private static final String PATH_DATA = "data";
    @Autowired
    private EstudianteRepository estudianteRepository;

    private void cargarEstudiantes() throws Exception {
        PopulatedEstudiantes poblador = new PopulatedEstudiantes(Path.of(PATH_DATA, "estudiantes.csv"), this.estudianteRepository);
        poblador.poblar();
    }

    @Override
    public void run(String... args) throws Exception {
        this.cargarEstudiantes();
    }
}
