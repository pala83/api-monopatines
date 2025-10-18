package integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import integrador.dto.CarreraDTO;
import integrador.entity.Carrera;
import integrador.repository.CarreraRepository;
import jakarta.transaction.Transactional;

public class CarreraService implements BaseService<CarreraDTO> {

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    @Transactional
    public List<CarreraDTO> findAll() throws Exception {
        return carreraRepository.findAll().stream().map(CarreraDTO::new).toList();
    }

    @Override
    @Transactional
    public CarreraDTO findById(Long id) throws Exception {
        try{
            Optional<Carrera> existente = this.carreraRepository.findById(id);
            return new CarreraDTO(existente.get());
        }catch(Exception e){
            throw new Exception("Carrera no encontrada");
        }
    }

    @Override
    @Transactional
    public CarreraDTO save(Object entity) throws Exception {
        try{
            return new CarreraDTO(this.carreraRepository.save((Carrera) entity));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarreraDTO update(Long id, Object entity) throws Exception {
        try{
            Optional<Carrera> existente = this.carreraRepository.findById(id);
            Carrera carreraExistente = existente.get();
            carreraExistente = carreraRepository.save((Carrera) entity);
            return new CarreraDTO(carreraExistente);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            if(carreraRepository.existsById(id)){
                this.carreraRepository.deleteById(id);
                return true;
            }else{
                throw new Exception("Carrera no encontrada");
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
