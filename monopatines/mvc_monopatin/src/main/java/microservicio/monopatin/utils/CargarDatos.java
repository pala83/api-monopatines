package microservicio.monopatin.utils;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import microservicio.monopatin.service.MonopatinService;
import microservicio.monopatin.service.ParadaService;
import microservicio.monopatin.utils.dataManager.PopulatedMonopatines;
import microservicio.monopatin.utils.dataManager.PopulatedParadas;

@Component
public class CargarDatos implements CommandLineRunner{
    private static final String PATH_DATA = "data";
    
    @Autowired
    private ParadaService paradaService;
    
    @Autowired
    private MonopatinService monopatinService;

    private void cargarMonopatines() throws Exception {
        PopulatedMonopatines populator = new PopulatedMonopatines(
            Paths.get(PATH_DATA, "monopatines.csv"),
            this.monopatinService,
            this.paradaService
        );
        populator.poblar();
    }

    private void cargarParadas() throws Exception {
        PopulatedParadas populator = new PopulatedParadas(
            Paths.get(PATH_DATA, "paradas.csv"),
            this.paradaService
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
