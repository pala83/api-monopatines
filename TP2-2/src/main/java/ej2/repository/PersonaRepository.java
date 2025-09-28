package ej2.repository;

import java.util.List;

import ej2.dto.PersonaDTO;
import ej2.model.Persona;

public interface PersonaRepository {
    void insertarDesdeCSV(String pathCSV);
    List<Persona> buscarTodos();
    List<PersonaDTO> personasPorCiudadDTO(String ciudad);
}
