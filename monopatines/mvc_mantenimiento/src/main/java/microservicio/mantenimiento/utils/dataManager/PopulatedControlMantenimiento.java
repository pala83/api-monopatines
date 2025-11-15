package microservicio.mantenimiento.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.mantenimiento.entity.ControlMantenimiento;
import microservicio.mantenimiento.repository.ControlMantenimientoRepository;

public class PopulatedControlMantenimiento extends Populated<ControlMantenimiento> {
    private ControlMantenimientoRepository controlMantenimientoRepository;

    public PopulatedControlMantenimiento(Path filePath, ControlMantenimientoRepository controlMantenimientoRepository) {
        super(filePath);
        this.controlMantenimientoRepository = controlMantenimientoRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<ControlMantenimiento> controles = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                ControlMantenimiento control = new ControlMantenimiento();

                control.setKilometraje(Double.parseDouble(record.get("kilometraje")));
                control.setUsoMinutos(Long.parseLong(record.get("uso_minutos")));
                control.setActivo(Boolean.parseBoolean(record.get("activo")));
                control.setUltimoMantenimiento(LocalDateTime.parse(record.get("ultimo_mantenimiento").trim()));

                controles.add(control);
            } catch (Exception e) {
                System.err.println("[PopulatedControlMantenimiento.poblar] ERROR parsing registro: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!controles.isEmpty()){
                this.controlMantenimientoRepository.saveAll(controles);
                System.out.println("[PopulatedControlMantenimiento.poblar] Se han poblado " + controles.size() + " controles de mantenimiento.");
            } else {
                System.out.println("[PopulatedControlMantenimiento.poblar] No hay controles para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedControlMantenimiento.poblar] ERROR guardando controles: " + e.getMessage());
        }
    }
}