package integrador.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import integrador.entity.Estudiante;
import integrador.repository.EstudianteRepository;

public class PopulatedEstudiantes extends Populated<Estudiante> {

    private EstudianteRepository estudianteRepository;

    public PopulatedEstudiantes(Path filePath, EstudianteRepository estudianteRepository) {
        super(filePath);
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        List<Estudiante> estudiantes = new ArrayList<>();
        
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord r : records){
            try {
                Estudiante e = new Estudiante();
                e.setDni(Long.parseLong(r.get("DNI")));
                e.setNombre(r.get("nombre"));
                e.setApellido(r.get("apellido"));
                e.setEdad(Integer.parseInt(r.get("edad")));
                e.setGenero(r.get("genero"));
                e.setCiudad(r.get("ciudad"));
                e.setLu(Integer.parseInt(r.get("LU")));
                estudiantes.add(e);
            } catch (Exception ex) {
                System.out.println("Error al parsear estudiante: " + ex.getMessage());
            }

        }
        try {
            if(!estudiantes.isEmpty()){
                estudianteRepository.saveAll(estudiantes);
                System.out.println("Estudiantes cargados exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al guardar estudiantes: " + e.getMessage());
        }
    }
}
