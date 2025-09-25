package ej1.repository;

import java.util.List;

import ej1.dto.PersonaDTO;
import ej1.model.Persona;

public interface PersonaRepository {
    void insertarDesdeCSV(String pathCSV);
    List<Persona> buscarTodos();
    List<Persona> buscarPorCiudad(String ciudad);
    List<PersonaDTO> buscarPorCiudad2(String ciudad);
}
