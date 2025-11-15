package microservicio.mantenimiento.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.mantenimiento.entity.RegistroMantenimiento;
import microservicio.mantenimiento.repository.RegistroMantenimientoRepository;

public class PopulatedRegistroMantenimiento extends Populated<RegistroMantenimiento> {
    private RegistroMantenimientoRepository registroMantenimientoRepository;

    public PopulatedRegistroMantenimiento(Path filePath, RegistroMantenimientoRepository registroMantenimientoRepository) {
        super(filePath);
        this.registroMantenimientoRepository = registroMantenimientoRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<RegistroMantenimiento> registros = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                RegistroMantenimiento registro = new RegistroMantenimiento();

                registro.setIdMonopatin(Long.parseLong(record.get("id_monopatin")));
                registro.setFechaInicio(LocalDateTime.parse(record.get("fecha_inicio").trim()));

                String fechaFin = record.get("fecha_fin");
                if(fechaFin != null && !fechaFin.isEmpty()) {
                    registro.setFechaFin(LocalDateTime.parse(fechaFin.trim()));
                }

                registros.add(registro);
            } catch (Exception e) {
                System.err.println("[PopulatedRegistroMantenimiento.poblar] ERROR parsing registro: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!registros.isEmpty()){
                this.registroMantenimientoRepository.saveAll(registros);
                System.out.println("[PopulatedRegistroMantenimiento.poblar] Se han poblado " + registros.size() + " registros de mantenimiento.");
            } else {
                System.out.println("[PopulatedRegistroMantenimiento.poblar] No hay registros para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedRegistroMantenimiento.poblar] ERROR guardando registros: " + e.getMessage());
        }
    }
}