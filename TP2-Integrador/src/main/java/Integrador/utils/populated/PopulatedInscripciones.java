package Integrador.utils.populated;

import java.util.List;

import Integrador.dto.InscripcionDTO;
import Integrador.model.Carrera;
import Integrador.model.Estudiante;
import Integrador.model.Inscripcion;
import Integrador.repository.CarreraRepository;
import Integrador.repository.CarreraRepositoryImp;
import Integrador.repository.EstudianteRepository;
import Integrador.repository.EstudianteRepositoryImp;
import Integrador.repository.InscripcionRepository;
import Integrador.repository.InscripcionRepositoryImp;

public class PopulatedInscripciones implements Populated<InscripcionDTO> {
    @Override
    public void poblar(List<InscripcionDTO> inscripciones) {
        InscripcionRepository ir = new InscripcionRepositoryImp();
        CarreraRepository cr = new CarreraRepositoryImp();
        EstudianteRepository er = new EstudianteRepositoryImp();
        for (InscripcionDTO dto : inscripciones) {
            Estudiante est = er.getById(dto.getId_estudiante());
            Carrera car = cr.getById(dto.getId_carrera());
            if(est != null && car != null) {
                Inscripcion ins = new Inscripcion();
                ins.setId(dto.getId());
                ins.setEstudiante(est);
                ins.setCarrera(car);
                ins.setInscripcion(dto.getInscripcion());
                ins.setGraduacion(dto.getGraduacion());
                ins.setAntiguedad(dto.getAntiguedad());
                ir.insert(ins);
            }
        }
    }
}
