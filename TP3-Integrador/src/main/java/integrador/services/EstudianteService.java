package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import integrador.dto.estudiante.EstudianteRequestDTO;
import integrador.dto.estudiante.EstudianteResponseDTO;
import integrador.entity.Estudiante;
import integrador.repository.EstudianteRepository;
import integrador.services.exceptions.BadRequestException;
import integrador.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;

@Service("EstudianteService")
public class EstudianteService implements BaseService<EstudianteRequestDTO, EstudianteResponseDTO> {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    @Transactional
    public List<EstudianteResponseDTO> findAll(){
        try{
            return this.estudianteRepository
                .findAll()
                .stream()
                .map(this::castResponse)
                .toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public List<EstudianteResponseDTO> findByGenero(String genero) {
        try {
            return this.estudianteRepository.findByGenero(genero)
                    .stream()
                    .map(this::castResponse)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public EstudianteResponseDTO findByLu(Integer lu) {
        return this.castResponse(this.estudianteRepository.findByLu(lu)
                .orElseThrow(() -> new NotFoundException("Estudiante (LU)", lu.longValue())));
    }

    @Transactional
    public List<EstudianteResponseDTO> findByCarreraAndCiudad(Long carreraId, String ciudad) {
        if (carreraId == null) {
            throw new BadRequestException("carreraId", null);
        }
        try {
            List<Estudiante> lista = (ciudad == null || ciudad.isBlank())
                ? this.estudianteRepository.findByInscripcionesCarreraId(carreraId)
                : this.estudianteRepository.findByCarreraIdAndCiudad(carreraId, ciudad);
            return lista.stream().map(this::castResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public List<EstudianteResponseDTO> findAllOrdered(String orderBy, String direction){
        String dir = direction == null ? "ASC" : direction.toUpperCase();
        try{
            return this.estudianteRepository
                .findAll(Sort.by(Sort.Direction.fromString(dir), orderBy))
                .stream()
                .map(this::castResponse)
                .toList();
        }catch (Exception e){
            throw new BadRequestException(orderBy, direction);
        }
    }

    @Override
    @Transactional
    public EstudianteResponseDTO findById(Long id){
        try{
            Optional<Estudiante> existente = this.estudianteRepository.findById(id);
            return this.castResponse(existente.get());
        }catch (Exception e){
            throw new NotFoundException("Estudiante", id);
        }
    }

    @Override
    @Transactional
    public EstudianteResponseDTO save(EstudianteRequestDTO request){
        try{
            Estudiante estudiante = new Estudiante();
            estudiante.setDni(request.getDni());
            estudiante.setNombre(request.getNombre());
            estudiante.setApellido(request.getApellido());
            estudiante.setEdad(request.getEdad());
            estudiante.setGenero(request.getGenero());
            estudiante.setCiudad(request.getCiudad());
            estudiante.setLu(request.getLu());

            Estudiante estudianteGuardado = this.estudianteRepository.save(estudiante);
            return this.castResponse(estudianteGuardado);
            
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id){
        try{
            if(estudianteRepository.existsById(id)){
                estudianteRepository.deleteById(id);
                return true;
            }
            else
                throw new Exception("El estudiante con id " + id + " no existe.");
        }catch (Exception e){
            throw new NotFoundException("Estudiante", id);
        }
    }

    private EstudianteResponseDTO castResponse(Estudiante estudiante) {
        return new EstudianteResponseDTO(
            estudiante.getDni(),
            estudiante.getNombre() + " " + estudiante.getApellido(),
            estudiante.getCiudad(),
            estudiante.getLu()
        );
    }
}
