package ej8.repository;

import java.util.List;

import ej8.dto.DireccionDTO;

public interface DireccionRepository {
    void insertarDesdeCSV(String pathCSV);
    List<DireccionDTO> direccion_de_persona(String nombre);

}
