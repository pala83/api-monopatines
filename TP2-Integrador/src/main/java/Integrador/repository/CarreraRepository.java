package Integrador.repository;

import java.util.List;

import Integrador.dto.CarreraDTO;
import Integrador.model.Carrera;

public interface CarreraRepository {
    void insert(Carrera carrera);
    Carrera getById(Integer id);
    List<CarreraDTO> getCarreras();
}
