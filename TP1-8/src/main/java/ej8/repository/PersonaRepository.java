package ej8.repository;

import java.util.List;

import ej8.dto.PersonaDTO;
import ej8.modelo.Persona;

public interface PersonaRepository {
    void insertarDesdeCSV(String pathCSV);
    List<Persona> buscarTodos();
    List<Persona> buscarPorCiudad(String ciudad);
    List<PersonaDTO> buscarPorCiudad2(String ciudad);
}
