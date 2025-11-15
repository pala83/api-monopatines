package microservicio.viaje.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;

import microservicio.viaje.entity.Pausa;
import microservicio.viaje.entity.Viaje;
import microservicio.viaje.repository.PausaRepository;
import microservicio.viaje.repository.ViajeRepository;

public class PopulatedPausas extends Populated<Pausa> {
    private PausaRepository pausaRepository;
    private ViajeRepository viajeRepository;

    public PopulatedPausas(Path filePath, PausaRepository pausaRepository, ViajeRepository viajeRepository) {
        super(filePath);
        this.pausaRepository = pausaRepository;
        this.viajeRepository = viajeRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<Pausa> pausas = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord record : records){
            try {
                Pausa p = new Pausa();

                // Buscar el viaje asociado
                Long viajeId = Long.parseLong(record.get("viajeId"));
                Optional<Viaje> viajeOpt = viajeRepository.findById(viajeId);
                if (viajeOpt.isPresent()) {
                    p.setViaje(viajeOpt.get());
                } else {
                    System.err.println("[PopulatedPausas.poblar] Viaje no encontrado con ID: " + viajeId);
                    continue;
                }

                p.setTiempoInicio(LocalDateTime.parse(record.get("tiempoInicio").trim()));

                if(record.isMapped("tiempoFin") && !record.get("tiempoFin").isEmpty()) {
                    p.setTiempoFin(LocalDateTime.parse(record.get("tiempoFin").trim()));
                }

                if(record.isMapped("duracionSegundos") && !record.get("duracionSegundos").isEmpty()) {
                    p.setDuracionSegundos(Long.parseLong(record.get("duracionSegundos")));
                }

                if(record.isMapped("extendido") && !record.get("extendido").isEmpty()) {
                    p.setExtendido(Boolean.parseBoolean(record.get("extendido")));
                }

                pausas.add(p);
            } catch (Exception e) {
                System.err.println("[PopulatedPausas.poblar] ERROR parsing registro de pausa: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }
        try {
            if(!pausas.isEmpty()){
                this.pausaRepository.saveAll(pausas);
                System.out.println("[PopulatedPausas.poblar] Se han poblado " + pausas.size() + " pausas.");
            } else {
                System.out.println("[PopulatedPausas.poblar] No hay pausas para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedPausas.poblar] ERROR guardando pausas: " + e.getMessage());
        }
    }
}