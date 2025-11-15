package microservicio.viaje.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.viaje.entity.Viaje;
import microservicio.viaje.entity.Ubicacion;
import microservicio.viaje.entity.EstadoViaje;
import microservicio.viaje.repository.ViajeRepository;

public class PopulatedViajes extends Populated<Viaje> {
    private ViajeRepository viajeRepository;

    public PopulatedViajes(Path filePath, ViajeRepository viajeRepository) {
        super(filePath);
        this.viajeRepository = viajeRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<Viaje> viajes = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                Viaje v = new Viaje();
                v.setIdUsuario(Long.parseLong(record.get("idUsuario")));
                v.setIdMonopatin(Long.parseLong(record.get("idMonopatin")));
                v.setIdCuenta(Long.parseLong(record.get("idCuenta")));
                v.setFechaInicio(LocalDateTime.parse(record.get("fechaInicio").trim()));

                Ubicacion ubicacionInicio = new Ubicacion();
                ubicacionInicio.setLatitud(Double.parseDouble(record.get("inicioLatitud")));
                ubicacionInicio.setLongitud(Double.parseDouble(record.get("inicioLongitud")));
                v.setUbicacionInicio(ubicacionInicio);

                String fechaFin = record.get("fechaFin");
                if(!fechaFin.isEmpty()) {
                    v.setFechaFin(LocalDateTime.parse(fechaFin.trim()));

                    // Solo si hay fechaFin, hay ubicación fin
                    Ubicacion ubicacionFin = new Ubicacion();
                    ubicacionFin.setLatitud(Double.parseDouble(record.get("finLatitud")));
                    ubicacionFin.setLongitud(Double.parseDouble(record.get("finLongitud")));
                    v.setUbicacionFin(ubicacionFin);

                    v.setDistanciaRecorrida(Double.parseDouble(record.get("distanciaRecorrida")));
                    v.setEstado(EstadoViaje.FINALIZADO);
                } else {
                    v.setEstado(EstadoViaje.EN_CURSO);
                }

                viajes.add(v);
            } catch (Exception e) {
                System.err.println("[PopulatedViajes.poblar] ERROR parsing registro de viaje: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!viajes.isEmpty()){
                this.viajeRepository.saveAll(viajes);
                System.out.println("[PopulatedViajes.poblar] Se han poblado " + viajes.size() + " viajes.");
            } else {
                System.out.println("[PopulatedViajes.poblar] No hay viajes para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedViajes.poblar] ERROR guardando viajes: " + e.getMessage());
        }
    }
}