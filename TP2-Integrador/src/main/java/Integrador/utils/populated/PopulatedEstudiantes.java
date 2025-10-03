package Integrador.utils.populated;

import java.util.List;

import Integrador.dto.EstudianteDTO;
import Integrador.model.Estudiante;
import Integrador.repository.EstudianteRepository;
import Integrador.repository.EstudianteRepositoryImp;

public class PopulatedEstudiantes implements Populated<EstudianteDTO> {

    @Override
    public void poblar(List<EstudianteDTO> estudiantes) {
        EstudianteRepository er = new EstudianteRepositoryImp();
        for (EstudianteDTO dto : estudiantes) {
            Estudiante e = new Estudiante();
            e.setDni(dto.getDni());
            e.setLu(dto.getLu());
            e.setNombre(dto.getNombre());
            e.setApellido(dto.getApellido());
            e.setGenero(dto.getGenero());
            e.setEdad(dto.getEdad());
            e.setCiudad(dto.getCiudad());
            er.insert(e);
        }
    }
}
