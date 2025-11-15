package microservicio.mantenimiento.utils;

import microservicio.mantenimiento.repository.ControlMantenimientoRepository;
import microservicio.mantenimiento.repository.RegistroMantenimientoRepository;
import microservicio.mantenimiento.utils.dataManager.PopulatedControlMantenimiento;
import microservicio.mantenimiento.utils.dataManager.PopulatedRegistroMantenimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class CargarDatos implements CommandLineRunner {
    private static final String PATH_DATA = "data";

    @Autowired
    private ControlMantenimientoRepository controlMantenimientoRepository;

    @Autowired
    private RegistroMantenimientoRepository registroMantenimientoRepository;

    private void cargarControlesMantenimiento() {
        try {
            if (controlMantenimientoRepository.count() == 0) {
                PopulatedControlMantenimiento populator = new PopulatedControlMantenimiento(
                        Paths.get(PATH_DATA, "controlMantenimiento.csv"),
                        this.controlMantenimientoRepository
                );
                populator.poblar();
                System.out.println("✅ Controles de mantenimiento cargados exitosamente");
            } else {
                System.out.println("ℹ️  Los controles de mantenimiento ya están cargados, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando controles de mantenimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarRegistrosMantenimiento() {
        try {
            if (registroMantenimientoRepository.count() == 0) {
                PopulatedRegistroMantenimiento populator = new PopulatedRegistroMantenimiento(
                        Paths.get(PATH_DATA, "registroMantenimiento.csv"),
                        this.registroMantenimientoRepository
                );
                populator.poblar();
                System.out.println("✅ Registros de mantenimiento cargados exitosamente");
            } else {
                System.out.println("ℹ️  Los registros de mantenimiento ya están cargados, omitiendo...");
            }
        } catch (Exception e) {
            System.err.println("❌ Error cargando registros de mantenimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando carga de datos de mantenimiento...");

        this.cargarControlesMantenimiento();
        this.cargarRegistrosMantenimiento();

        System.out.println("🎉 Carga de datos de mantenimiento completada");
    }
}