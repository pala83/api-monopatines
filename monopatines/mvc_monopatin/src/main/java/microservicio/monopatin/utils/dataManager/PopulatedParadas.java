package microservicio.monopatin.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import org.apache.commons.csv.CSVRecord;

import microservicio.monopatin.dto.parada.ParadaRequest;
import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.entity.Ubicacion;
import microservicio.monopatin.service.ParadaService;

public class PopulatedParadas extends Populated<Parada> {
    private ParadaService paradaService;
    
    public PopulatedParadas(Path filePath, ParadaService paradaService) {
        super(filePath);
        this.paradaService = paradaService;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        int paradasCargadas = 0;
        Iterable<CSVRecord> records = this.read();
        
        for(CSVRecord record : records){
            try {
                ParadaRequest request = new ParadaRequest();
                request.setNombre(record.get("nombre"));
                
                Ubicacion ubicacion = new Ubicacion(
                    Double.parseDouble(record.get("latitud")),
                    Double.parseDouble(record.get("longitud"))
                );
                request.setUbicacion(ubicacion);
                request.setCapacidad(Integer.parseInt(record.get("capacidad")));
                paradaService.save(request);
                paradasCargadas++;
                
            } catch (Exception e) {
                System.err.println("[PopulatedParadas.poblar] ERROR procesando parada: " + record.toString() + " - " + e.getMessage());
            }
        }
        
        System.out.println("[PopulatedParadas.poblar] Se han poblado " + paradasCargadas + " paradas.");
    }

}
