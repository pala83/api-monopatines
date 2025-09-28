package ej2.repository;

import java.util.List;

import ej2.dto.PersonaDTO;
import ej2.dto.PersonaSocioDTO;
import ej2.dto.TurnoDTO;

public interface TurnoRepository {
    void insertarDesdeCSV(String pathCSV);
    // Recuperar todas las personas asignadas a un turno (por id)
    List<PersonaDTO> selectJugadoresPorId(Integer turnoId);
    // Recuperar personas de un turno marcando si son socios
    List<PersonaSocioDTO> selectJugadoresConSocioPorId(Integer turnoId);
    TurnoDTO selectPorId(Integer id);
    List<TurnoDTO> selectTodo();
}
