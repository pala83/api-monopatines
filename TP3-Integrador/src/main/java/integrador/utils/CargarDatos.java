package integrador.utils;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import integrador.repository.CarreraRepository;
import integrador.repository.EstudianteRepository;
import integrador.repository.InscripcionRepository;
import integrador.utils.dataManager.PopulatedCarreras;
import integrador.utils.dataManager.PopulatedEstudiantes;
import integrador.utils.dataManager.PopulatedInscripciones;

@Component
public class CargarDatos implements CommandLineRunner {
    private static final String PATH_DATA = "data";
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    private void cargarEstudiantes() throws Exception {
        PopulatedEstudiantes tablaEstudiantes = new PopulatedEstudiantes(Path.of(PATH_DATA, "estudiantes.csv"), this.estudianteRepository);
        tablaEstudiantes.poblar();
    }

    private void cargarCarreras() throws Exception {
        PopulatedCarreras tablaCarreras = new PopulatedCarreras(Path.of(PATH_DATA, "carreras.csv"), this.carreraRepository);
        tablaCarreras.poblar();
    }

    private void cargarInscripciones() throws Exception {
        PopulatedInscripciones tablaInscripciones = new PopulatedInscripciones(Path.of(PATH_DATA, "estudianteCarrera.csv"), this.inscripcionRepository, this.carreraRepository, this.estudianteRepository);
        tablaInscripciones.poblar();
    }

    @Override
    public void run(String... args) throws Exception {
        this.cargarEstudiantes();
        this.cargarCarreras();
        this.cargarInscripciones();
    }
}
