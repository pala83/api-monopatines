package Integrador.repository;

import java.util.List;

import Integrador.dto.EstudianteDTO;
import Integrador.model.Estudiante;

public interface EstudianteRepository {
    void insert(Estudiante estudiante);
    Estudiante getById(Integer dni);
    EstudianteDTO getByLu(Integer lu);
    List<EstudianteDTO> getEstudiantes();
    List<EstudianteDTO> getEstudiantesConOrden(String atributo, String orden);
    List<EstudianteDTO> getEstudiantesPorGenero(String genero);
}
