package integrador.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import integrador.entity.Carrera;
import integrador.repository.CarreraRepository;

public class PopulatedCarreras extends Populated<Carrera> {
    private CarreraRepository carreraRepository;
    public PopulatedCarreras(Path filePath, CarreraRepository carreraRepository) {
        super(filePath);
        this.carreraRepository = carreraRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException{
        List<Carrera> carreras = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord r : records){
            try {
                Carrera c = new Carrera();
                c.setId(Long.parseLong(r.get("id_carrera")));
                c.setNombre(r.get("carrera"));
                c.setDuracion(Integer.parseInt(r.get("duracion")));
                carreras.add(c);
            } catch (Exception ex) {
                System.out.println("Error al parsear carrera: " + ex.getMessage());

            }
        }
        try {
            if(!carreras.isEmpty()){
                carreraRepository.saveAll(carreras);
                System.out.println("Carreras cargadas exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al guardar carreras: " + e.getMessage());
        }
    }
}