package integrador.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import integrador.entity.Carrera;
import integrador.entity.Estudiante;
import integrador.entity.Inscripcion;
import integrador.repository.CarreraRepository;
import integrador.repository.EstudianteRepository;
import integrador.repository.InscripcionRepository;

public class PopulatedInscripciones extends Populated<Inscripcion> {
    private InscripcionRepository inscripcionRepository;
    private CarreraRepository cr;
    private EstudianteRepository er;
    public PopulatedInscripciones(
        Path filePath, 
        InscripcionRepository inscripcionRepository, 
        CarreraRepository cr, 
        EstudianteRepository er
    ) {
        super(filePath);
        this.inscripcionRepository = inscripcionRepository;
        this.cr = cr;
        this.er = er;
    }

    @Override
    public void poblar() throws IOException, ParseException{
        List<Inscripcion> inscripciones = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord r : records){
            try {
                Inscripcion i = new Inscripcion();
                Estudiante nuevoEstudiante = er.findById(Long.parseLong(r.get("id_estudiante"))).orElse(null);
                Carrera nuevaCarrera = cr.findById(Long.parseLong(r.get("id_carrera"))).orElse(null);
                if(nuevoEstudiante == null || nuevaCarrera == null)
                    System.out.println("Estudiante o Carrera no encontrada para Inscripcion: " + r.toString());
                else {
                    int graduacion = Integer.parseInt(r.get("graduacion"));
                    i.setEstudiante(nuevoEstudiante);
                    i.setCarrera(nuevaCarrera);
                    i.setInscripcion(Integer.parseInt(r.get("inscripcion")));
                    i.setGraduacion(graduacion == 0 ? null : graduacion);
                    i.setAntiguedad(Integer.parseInt(r.get("antiguedad")));
                    inscripciones.add(i);
                }
            } catch (Exception ex) {
                System.out.println("Error al parsear inscripción: " + ex.getMessage());
            }
        }
        try {
            if(!inscripciones.isEmpty()){
                inscripcionRepository.saveAll(inscripciones);
                System.out.println("Inscripciones cargadas exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al guardar inscripciones: " + e.getMessage());
        }
    }
}