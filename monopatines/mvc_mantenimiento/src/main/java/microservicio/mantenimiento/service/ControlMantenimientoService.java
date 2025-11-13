package microservicio.mantenimiento.service;

import java.time.LocalDateTime;
import java.util.List;

import microservicio.mantenimiento.client.MonopatinClient;
import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoRequest;
import microservicio.mantenimiento.dto.controlMantenimiento.ControlMantenimientoResponse;
import microservicio.mantenimiento.entity.ControlMantenimiento;
import microservicio.mantenimiento.repository.ControlMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service("controlMantenimientoService")
public class ControlMantenimientoService implements BaseService<ControlMantenimientoRequest, ControlMantenimientoResponse> {

    @Autowired
    private ControlMantenimientoRepository controlMantenimientoRepository;

    @Autowired
    private MonopatinClient monopatinClient;

    @Override
    @Transactional(readOnly = true)
    public List<ControlMantenimientoResponse> findAll() {
        return controlMantenimientoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ControlMantenimientoResponse findById(Long id) {
        ControlMantenimiento control = controlMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Control de mantenimiento con id " + id + " no encontrado"));
        return toResponse(control);
    }

    @Override
    @Transactional
    public ControlMantenimientoResponse save(ControlMantenimientoRequest req) {
        ControlMantenimiento control = new ControlMantenimiento();
        control.setKilometraje(req.getKilometraje());
        control.setUsoMinutos(req.getUsoMinutos());
        control.setActivo(req.getActivo());
        ControlMantenimiento saved = controlMantenimientoRepository.save(control);
        if (req.getIdMonopatin() != null) {
            monopatinClient.actualizarEstado(req.getIdMonopatin(), "MANTENIMIENTO");
        }
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ControlMantenimientoResponse update(Long id, ControlMantenimientoRequest req) {
        ControlMantenimiento control = controlMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Control de mantenimiento con id " + id + " no encontrado"));
        control.setKilometraje(req.getKilometraje());
        control.setUsoMinutos(req.getUsoMinutos());
        control.setActivo(req.getActivo());
        ControlMantenimiento updated = controlMantenimientoRepository.save(control);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ControlMantenimiento control = controlMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Control de mantenimiento con id " + id + " no encontrado"));
        controlMantenimientoRepository.delete(control);
    }

    @Transactional
    public ControlMantenimientoResponse finalizarMantenimiento(Long id, Long idMonopatin) {
        ControlMantenimiento control = controlMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Control de mantenimiento con id " + id + " no encontrado"));
        control.setActivo(false);
        ControlMantenimiento updated = controlMantenimientoRepository.save(control);
        if (idMonopatin != null) {
            monopatinClient.actualizarEstado(idMonopatin, "DISPONIBLE");
        }
        return toResponse(updated);
    }
    @Transactional(readOnly = true)
    public List<ControlMantenimientoResponse> findByActivo(Boolean activo) {
        return controlMantenimientoRepository.findByActivo(activo)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    private ControlMantenimientoResponse toResponse(ControlMantenimiento control) {
        ControlMantenimientoResponse resp = new ControlMantenimientoResponse();
        resp.setId(control.getId());
        resp.setKilometraje(control.getKilometraje());
        resp.setUsoMinutos(control.getUsoMinutos());
        resp.setActivo(control.getActivo());
        resp.setUltimoMantenimiento(control.getUltimoMantenimiento());
        return resp;
    }
}