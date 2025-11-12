package microservicio.monopatin.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.entity.Ubicacion;
import microservicio.monopatin.repository.ParadaRepository;

public class PopulatedParadas extends Populated<Parada> {
    private ParadaRepository paradaRepository;
    
    public PopulatedParadas(Path filePath, ParadaRepository paradaRepository) {
        super(filePath);
        this.paradaRepository = paradaRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        List<Parada> paradas = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord record : records){
            try {
                Parada p = new Parada();
                p.setNombre(record.get("nombre"));
                Ubicacion u = new Ubicacion(
                    Double.parseDouble(record.get("latitud")),
                    Double.parseDouble(record.get("longitud"))
                );
                p.setUbicacion(u);
                p.setCapacidad(Integer.parseInt(record.get("capacidad")));
                paradas.add(p);
            } catch (Exception e) {
                System.err.println("[PopulatedParadas.poblar] ERROR parsing registro de parada: " + record.toString());
            }
        }
        try {
            if(!paradas.isEmpty()){
                this.paradaRepository.saveAll(paradas);
                System.out.println("[PopulatedParadas.poblar] Se han poblado " + paradas.size() + " paradas.");
            } else {
                System.out.println("[PopulatedParadas.poblar] No hay paradas para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedParadas.poblar] ERROR guardando paradas: " + e.getMessage());
        }
    }

}
