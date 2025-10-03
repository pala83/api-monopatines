package Integrador.repository;

import java.util.List;

import Integrador.dto.InscripcionDTO;
import Integrador.model.Inscripcion;

public interface InscripcionRepository {
    void insert(Inscripcion inscripcion);
    InscripcionDTO getById(Integer id);
    List<InscripcionDTO> getInscripciones();
}
