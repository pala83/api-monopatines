package ej1.repository;

import java.util.List;

import ej1.dto.DireccionDTO;

public interface DireccionRepository {
    void insertarDesdeCSV(String pathCSV);
    List<DireccionDTO> direccion_de_persona(String nombre);

}
