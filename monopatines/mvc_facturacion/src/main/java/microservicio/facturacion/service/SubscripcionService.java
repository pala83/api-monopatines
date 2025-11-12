package microservicio.facturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import microservicio.facturacion.dto.subscripcion.SubscripcionRequest;
import microservicio.facturacion.dto.subscripcion.SubscripcionResponse;
import microservicio.facturacion.entity.Subscripcion;
import microservicio.facturacion.repository.SubscripcionRepository;

@Service("SubscripcionService")
public class SubscripcionService implements BaseService<SubscripcionRequest, SubscripcionResponse> {
    @Autowired
    private SubscripcionRepository subscripcionRepository;

    @Override
    @Transactional
    public List<SubscripcionResponse> findAll() {
        return subscripcionRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public SubscripcionResponse findById(Long id) {
        return toResponse(
            this.subscripcionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Subscripcion con id " + id + " no encontrado")));
    }

    @Override
    @Transactional
    public SubscripcionResponse save(SubscripcionRequest request) {
        Subscripcion s = new Subscripcion();
        s.setIdCuenta(request.getIdCuenta());
        s.setPeriodo(request.getPeriodoFacturacion());
        s.setFechaPago(request.getFechaPago());
        s.setMonto(request.getMonto());
        s.setEstado(request.getEstado());
        return this.toResponse(subscripcionRepository.save(s));
    }

    @Override
    @Transactional
    public SubscripcionResponse update(Long id, SubscripcionRequest req) {
        Subscripcion s = subscripcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscripcion con id " + id + " no encontrado"));
        s.setIdCuenta(req.getIdCuenta());
        s.setPeriodo(req.getPeriodoFacturacion());
        s.setFechaPago(req.getFechaPago());
        s.setMonto(req.getMonto());
        s.setEstado(req.getEstado());
        return this.toResponse(subscripcionRepository.save(s));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.subscripcionRepository.delete(
            this.subscripcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscripcion con id " + id + " no encontrado"))
        );
    }

    private SubscripcionResponse toResponse(Subscripcion subscripcion) {
        return new SubscripcionResponse(
            subscripcion.getIdCuenta(),
            subscripcion.getPeriodo(),
            subscripcion.getFechaPago(),
            subscripcion.getMonto(),
            subscripcion.getEstado()
        );
    }
}
