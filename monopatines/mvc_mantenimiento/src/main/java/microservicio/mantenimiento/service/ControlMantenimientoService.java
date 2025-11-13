package microservicio.mantenimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoRequest;
import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoResponse;
import microservicio.mantenimiento.repository.ControlMantenimientoRepository;

@Service("controlMantenimientoService")
public class ControlMantenimientoService implements BaseService<ControlMantenimientoRequest, ControlMantenimientoResponse> {
    @Autowired
    private ControlMantenimientoRepository controlMantenimientoRepository;

    @Override
    public List<ControlMantenimientoResponse> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ControlMantenimientoResponse findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ControlMantenimientoResponse save(ControlMantenimientoRequest entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public ControlMantenimientoResponse update(Long id, ControlMantenimientoRequest entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


}
