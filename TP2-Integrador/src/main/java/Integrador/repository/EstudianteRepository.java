package Integrador.repository;

import java.util.List;

import Integrador.dto.EstudianteDTO;

public interface EstudianteRepository {
    void cargarEstudiantes(String archivo);
    List<EstudianteDTO> getEstudiantes();
}
