package microservicio.monopatin.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import microservicio.monopatin.repository.MonopatinRepository;
import microservicio.monopatin.repository.ParadaRepository;
import microservicio.monopatin.utils.dataManager.PopulatedMonopatines;
import microservicio.monopatin.utils.dataManager.PopulatedParadas;

@Component
public class CargarDatos implements CommandLineRunner{
    private static final String PATH_DATA = "data";
    @Autowired
    private ParadaRepository paradaRepository;
    @Autowired
    private MonopatinRepository monopatinRepository;

    private void cargarMonopatines() throws Exception {
        PopulatedMonopatines populator = new PopulatedMonopatines(
            java.nio.file.Paths.get(PATH_DATA, "monopatines.csv"),
            this.monopatinRepository,
            this.paradaRepository
        );
        populator.poblar();
    }

    private void cargarParadas() throws Exception {
        PopulatedParadas populator = new PopulatedParadas(
            java.nio.file.Paths.get(PATH_DATA, "paradas.csv"),
            this.paradaRepository
        );
        populator.poblar();
    }

    @Override
    public void run(String... args) throws Exception {
        // Primero cargar paradas, luego monopatines que dependen de esas paradas
        this.cargarParadas();
        this.cargarMonopatines();
    }

}
