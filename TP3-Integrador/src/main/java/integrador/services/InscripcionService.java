package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import integrador.dto.InscripcionDTO;
import integrador.entity.Inscripcion;
import integrador.repository.InscripcionRepository;
import jakarta.transaction.Transactional;

public class InscripcionService implements BaseService<InscripcionDTO> {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    @Transactional
    public List<InscripcionDTO> findAll() throws Exception {
        return inscripcionRepository.findAll().stream().map(InscripcionDTO::new).toList();
    }

    @Override
    @Transactional
    public InscripcionDTO findById(Long id) throws Exception {
        try{
            Optional<Inscripcion> existente = this.inscripcionRepository.findById(id);
            return new InscripcionDTO(existente.get());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public InscripcionDTO save(Object entity) throws Exception {
        try{
            return new InscripcionDTO(this.inscripcionRepository.save((Inscripcion) entity));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public InscripcionDTO update(Long id, Object entity) throws Exception {
        try{
            Optional<Inscripcion> existente = this.inscripcionRepository.findById(id);
            Inscripcion inscripcion = existente.get();
            inscripcion = inscripcionRepository.save((Inscripcion) entity);
            return new InscripcionDTO(inscripcion);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (inscripcionRepository.existsById(id)) {
                this.inscripcionRepository.deleteById(id);
                return true;
            } else {
                throw new Exception("No se encontró la inscripción");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
