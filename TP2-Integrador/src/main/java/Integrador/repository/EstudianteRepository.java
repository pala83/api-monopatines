package Integrador.repository;

import java.util.List;

import Integrador.dto.EstudianteDTO;
import Integrador.model.Estudiante;

public interface EstudianteRepository {
    void insert(Estudiante estudiante);
    Estudiante getById(Integer dni);
    List<EstudianteDTO> getEstudiantes();
}
