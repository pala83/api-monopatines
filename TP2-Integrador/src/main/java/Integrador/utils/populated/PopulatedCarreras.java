package Integrador.utils.populated;

import java.util.List;

import Integrador.dto.CarreraDTO;
import Integrador.model.Carrera;
import Integrador.repository.CarreraRepository;
import Integrador.repository.CarreraRepositoryImp;

public class PopulatedCarreras implements Populated<CarreraDTO> {
    @Override
    public void poblar(List<CarreraDTO> carreras) {
        CarreraRepository cr = new CarreraRepositoryImp();
        for (CarreraDTO dto : carreras) {
            Carrera c = new Carrera();
            c.setId(dto.getId_carrera());
            c.setNombre(dto.getCarrera());
            c.setDuracion(dto.getDuracion());
            cr.insert(c);
        }
    }

}
