package microservicio.viaje.utils;

import microservicio.viaje.repository.PausaRepository;
import microservicio.viaje.repository.ViajeRepository;
import microservicio.viaje.utils.dataManager.PopulatedPausas;
import microservicio.viaje.utils.dataManager.PopulatedViajes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class CargarDatos implements CommandLineRunner {
    private static final String PATH_DATA = "data";

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaRepository pausaRepository;

    private void cargarViajes() {
        try {
            // Verificar si ya hay datos para no duplicar
            if (viajeRepository.count() == 0) {
                PopulatedViajes populator = new PopulatedViajes(
                        Paths.get(PATH_DATA, "viajes.csv"),
                        this.viajeRepository
                );
                populator.poblar();
                System.out.println("✅ Viajes cargados exitosamente");
            } else {
                System.out.println("ℹ️  Los viajes ya están cargados, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando viajes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarPausas() {
        try {
            // Verificar si ya hay datos para no duplicar
            if (pausaRepository.count() == 0) {
                PopulatedPausas populator = new PopulatedPausas(
                        Paths.get(PATH_DATA, "pausas.csv"),
                        this.pausaRepository,
                        this.viajeRepository
                );
                populator.poblar();
                System.out.println("✅ Pausas cargadas exitosamente");
            } else {
                System.out.println("ℹ️  Las pausas ya están cargadas, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando pausas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando carga de datos...");

        // 1. Primero cargar viajes (entidades padre)
        this.cargarViajes();

        // 2. Luego cargar pausas (dependen de viajes existentes)
        this.cargarPausas();

        System.out.println("🎉 Carga de datos completada");
    }
}