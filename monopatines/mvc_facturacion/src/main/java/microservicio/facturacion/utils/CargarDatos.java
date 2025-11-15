package microservicio.facturacion.utils;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import microservicio.facturacion.repository.CargaRepository;
import microservicio.facturacion.repository.SubscripcionRepository;
import microservicio.facturacion.repository.TarifaRepository;
import microservicio.facturacion.utils.dataManager.PopulatedCargas;
import microservicio.facturacion.utils.dataManager.PopulatedSubscripciones;
import microservicio.facturacion.utils.dataManager.PopulatedTarifas;

@Component
public class CargarDatos implements CommandLineRunner {
    private static final String PATH_DATA = "data";

    @Autowired
    private CargaRepository cargaRepository;

    @Autowired
    private SubscripcionRepository subscripcionRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    private void cargarCargas() {
        try {
            if (cargaRepository.count() == 0) {
                PopulatedCargas populator = new PopulatedCargas(
                        Paths.get(PATH_DATA, "cargas.csv"),
                        this.cargaRepository
                );
                populator.poblar();
                System.out.println("✅ Cargas cargadas exitosamente");
            } else {
                System.out.println("ℹ️  Las cargas ya están cargadas, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando cargas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarSubscripciones() {
        try {
            if (subscripcionRepository.count() == 0) {
                PopulatedSubscripciones populator = new PopulatedSubscripciones(
                        Paths.get(PATH_DATA, "subscripciones.csv"),
                        this.subscripcionRepository
                );
                populator.poblar();
                System.out.println("✅ Subscripciones cargadas exitosamente");
            } else {
                System.out.println("ℹ️  Las subscripciones ya están cargadas, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando subscripciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarTarifas() {
        try {
            if (tarifaRepository.count() == 0) {
                PopulatedTarifas populator = new PopulatedTarifas(
                        Paths.get(PATH_DATA, "tarifas.csv"),
                        this.tarifaRepository
                );
                populator.poblar();
                System.out.println("✅ Tarifas cargadas exitosamente");
            } else {
                System.out.println("ℹ️  Las tarifas ya están cargadas, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando tarifas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando carga de datos de facturación...");

        // Orden lógico: tarifas -> subscripciones -> cargas
        this.cargarTarifas();
        this.cargarSubscripciones();
        this.cargarCargas();

        System.out.println("🎉 Carga de datos de facturación completada");
    }
}