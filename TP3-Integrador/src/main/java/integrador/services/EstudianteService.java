package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import integrador.dto.EstudianteDTO;
import integrador.entity.Estudiante;
import integrador.repository.EstudianteRepository;
import jakarta.transaction.Transactional;

@Service("EstudianteService")
public class EstudianteService implements BaseService<EstudianteDTO> {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    @Transactional
    public List<EstudianteDTO> findAll() throws Exception {
        return this.estudianteRepository
            .findAll()
            .stream()
            .map(EstudianteDTO::new)
            .toList();
    }

    @Override
    @Transactional
    public EstudianteDTO findById(Long id) throws Exception {
        try{
            Optional<Estudiante> existente = this.estudianteRepository.findById(id);
            return new EstudianteDTO(existente.get());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public EstudianteDTO save(Object entity) throws Exception {
        try{
            return new EstudianteDTO(this.estudianteRepository.save((Estudiante) entity));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public EstudianteDTO update(Long id, Object entity) throws Exception {
        try{
            Optional<Estudiante> existente = this.estudianteRepository.findById(id);
            Estudiante estudianteExistente = existente.get();
            estudianteExistente = estudianteRepository.save((Estudiante) entity);
            return new EstudianteDTO(estudianteExistente);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            if(estudianteRepository.existsById(id)){
                estudianteRepository.deleteById(id);
                return true;
            }
            else
                throw new Exception("El estudiante con id " + id + " no existe.");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
