package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import integrador.dto.inscripcion.InscripcionReporteDTO;
import integrador.dto.inscripcion.InscripcionRequestDTO;
import integrador.dto.inscripcion.InscripcionResponseDTO;
import integrador.entity.Carrera;
import integrador.entity.Estudiante;
import integrador.entity.Inscripcion;
import integrador.repository.CarreraRepository;
import integrador.repository.EstudianteRepository;
import integrador.repository.InscripcionRepository;
import integrador.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;

@Service("InscripcionService")
public class InscripcionService implements BaseService<InscripcionRequestDTO, InscripcionResponseDTO> {
    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CarreraRepository carreraRepository;

    @Transactional
    public List<InscripcionReporteDTO> generarReporte(){
        return this.inscripcionRepository
            .getReport()
            .stream()
            .map(obj -> new InscripcionReporteDTO(
                (String) obj[0],
                ((Number) obj[1]).intValue(),
                ((Number) obj[2]).longValue(),
                ((Number) obj[3]).longValue()))
            .toList();
    }

    @Override
    @Transactional
    public List<InscripcionResponseDTO> findAll(){
        try{
            return this.inscripcionRepository
                .findAll()
                .stream()
                .map(this::castResponse)
                .toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public InscripcionResponseDTO findById(Long id){
        try{
            Optional<Inscripcion> existente = this.inscripcionRepository.findById(id);
            return this.castResponse(existente.get());
        }catch (Exception e){
            throw new NotFoundException("Inscripcion", id);
        }
    }

    @Override
    @Transactional
    public InscripcionResponseDTO save(InscripcionRequestDTO request){
        Estudiante estudiante = this.estudianteRepository
                .findById(request.getId_estudiante())
                .orElseThrow(() -> new NotFoundException("Estudiante", request.getId_estudiante()));

        Carrera carrera = this.carreraRepository
                .findById(request.getId_carrera())
                .orElseThrow(() -> new NotFoundException("Carrera", request.getId_carrera()));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCarrera(carrera);
        inscripcion.setInscripcion(request.getInscripcion());
        inscripcion.setGraduacion(request.getGraduacion());
        inscripcion.setAntiguedad(request.getAntiguedad());

        Inscripcion inscripcionGuardada = this.inscripcionRepository.save(inscripcion);
        return this.castResponse(inscripcionGuardada);
    }

    @Override
    @Transactional
    public boolean delete(Long id){
        try{
            if(inscripcionRepository.existsById(id)){
                inscripcionRepository.deleteById(id);
                return true;
            }
            else
                throw new Exception("La inscripcion con id " + id + " no existe.");
        }catch (Exception e){
            throw new NotFoundException("Inscripcion", id);
        }
    }

    private InscripcionResponseDTO castResponse(Inscripcion inscripcion) {
        return new InscripcionResponseDTO(
            inscripcion.getEstudiante().getDni(),
            inscripcion.getCarrera().getId(),
            inscripcion.getInscripcion(),
            inscripcion.getGraduacion(),
            inscripcion.getAntiguedad()
        );
    }
}
