package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import integrador.dto.carrera.CarreraConEstudiantesDTO;
import integrador.dto.carrera.CarreraRequestDTO;
import integrador.dto.carrera.CarreraResponseDTO;
import integrador.entity.Carrera;
import integrador.repository.CarreraRepository;
import integrador.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;

@Service("CarreraService")
public class CarreraService implements BaseService<CarreraRequestDTO, CarreraResponseDTO> {
    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    @Transactional
    public List<CarreraResponseDTO> findAll(){
        try{
            return this.carreraRepository
                .findAll()
                .stream()
                .map(this::castResponse)
                .toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public List<CarreraConEstudiantesDTO> findCarrerasConInscriptosOrdenado() {
        return this.carreraRepository
            .findCarrerasConInscriptosOrdenado()
            .stream()
            .map(dto -> new CarreraConEstudiantesDTO(dto.getNombre(), dto.getCant_estudiantes()))
            .toList();
    }

    @Override
    @Transactional
    public CarreraResponseDTO findById(Long id){
        try{
            Optional<Carrera> existente = this.carreraRepository.findById(id);
            return this.castResponse(existente.get());
        }catch (Exception e){
            throw new NotFoundException("Carrera", id);
        }
    }

    @Override
    @Transactional
    public CarreraResponseDTO save(CarreraRequestDTO request){
        try{
            Carrera carrera = new Carrera();
            carrera.setId(request.getId());
            carrera.setNombre(request.getNombre());
            carrera.setDuracion(request.getDuracion());

            Carrera carreraGuardada = this.carreraRepository.save(carrera);
            return this.castResponse(carreraGuardada);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

        @Override
    @Transactional
    public boolean delete(Long id){
        try{
            if(carreraRepository.existsById(id)){
                carreraRepository.deleteById(id);
                return true;
            }
            else
                throw new Exception("La carrera con id " + id + " no existe.");
        }catch (Exception e){
            throw new NotFoundException("Carrera", id);
        }
    }

    private CarreraResponseDTO castResponse(Carrera carrera) {
        return new CarreraResponseDTO(
            carrera.getNombre(),
            carrera.getDuracion()
        );
    }

}
