package microservicio.monopatin.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import org.apache.commons.csv.CSVRecord;

import microservicio.monopatin.dto.monopatin.MonopatinRequest;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Monopatin;
import microservicio.monopatin.entity.Ubicacion;
import microservicio.monopatin.service.MonopatinService;
import microservicio.monopatin.service.ParadaService;

public class PopulatedMonopatines extends Populated<Monopatin> {
    private MonopatinService monopatinService;
    private ParadaService paradaService;

    public PopulatedMonopatines(Path filePath, MonopatinService monopatinService, ParadaService paradaService) {
        super(filePath);
        this.monopatinService = monopatinService;
        this.paradaService = paradaService;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        int monopatinesCargados = 0;
        int monopatinesSinParada = 0;
        int monopatinesRechazados = 0;
        
        Iterable<CSVRecord> records = this.read();
        
        for(CSVRecord record : records){
            try{ 
                Long paradaId = Long.parseLong(record.get("parada"));
                
                try {
                    paradaService.findById(paradaId);
                } catch (Exception e) {
                    System.err.println("[PopulatedMonopatines.poblar] Parada con ID " + paradaId + " no encontrada. Monopatín " + record.get("codigoQR") + " será creado sin parada.");
                    paradaId = null;
                }
                
                MonopatinRequest request = new MonopatinRequest();
                request.setMarca(record.get("marca"));
                request.setCodigoQR(record.get("codigoQR"));
                request.setKmTotales(Double.parseDouble(record.get("kmTotales")));
                request.setEstado(EstadoMonopatin.valueOf(record.get("estado")));
                
                Ubicacion ubicacion = new Ubicacion(
                    Double.parseDouble(record.get("latitud")),
                    Double.parseDouble(record.get("longitud"))
                );
                request.setUbicacionActual(ubicacion);
                request.setParadaActualId(paradaId);

                var monopatinResponse = monopatinService.save(request);
                
                if (paradaId != null) {
                    try {
                        monopatinService.ubicarEnParada(monopatinResponse.getId(), paradaId);
                        monopatinesCargados++;
                    } catch (IllegalStateException e) {
                        System.err.println("[PopulatedMonopatines.poblar] No se pudo ubicar monopatín " + 
                            record.get("codigoQR") + " en parada " + paradaId + ": " + e.getMessage());
                        monopatinesRechazados++;
                    }
                } else {
                    monopatinesSinParada++;
                }
                
            } catch (Exception e) {
                System.err.println("[PopulatedMonopatines.poblar] ERROR procesando monopatín: " + 
                    record.toString() + " - " + e.getMessage());
            }
        }
        
        System.out.println("[PopulatedMonopatines.poblar] Resumen de carga:");
        System.out.println("  - Monopatines ubicados en paradas: " + monopatinesCargados);
        System.out.println("  - Monopatines sin parada asignada: " + monopatinesSinParada);
        System.out.println("  - Monopatines rechazados (parada llena): " + monopatinesRechazados);
        System.out.println("  - Total monopatines cargados: " + (monopatinesCargados + monopatinesSinParada + monopatinesRechazados));
    }
}
