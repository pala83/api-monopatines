package microservicio.facturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import microservicio.facturacion.dto.carga.CargaRequest;
import microservicio.facturacion.dto.carga.CargaResponse;
import microservicio.facturacion.entity.Carga;
import microservicio.facturacion.repository.CargaRepository;

@Service("CargaService")
public class CargaService implements BaseService<CargaRequest, CargaResponse> {
    @Autowired
    private CargaRepository cargaRepository;

    @Override
    @Transactional
    public List<CargaResponse> findAll() {
        return cargaRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public CargaResponse findById(Long id) {
        return toResponse(
            this.cargaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Carga con id " + id + " no encontrado")));
    }

    @Override
    @Transactional
    public CargaResponse save(CargaRequest request) {
        Carga c = new Carga();
        c.setViajeId(request.getViajeId());
        c.setCuentaId(request.getCuentaId());
        c.setMontoTotal(request.getMontoTotal());
        c.setCargaNormal(request.getCargaNormal());
        c.setCargaPausaExtendida(request.getCargaPausaExtendida());
        c.setDuracionMinutos(request.getDuracionMinutos());
        c.setDuracionPausaMinutos(request.getDuracionPausaMinutos());
        c.setKmRecorridos(request.getKmRecorridos());
        c.setFechaCarga(request.getFechaCarga());
        return this.toResponse(cargaRepository.save(c));
    }

    @Override
    @Transactional
    public CargaResponse update(Long id, CargaRequest req) {
        Carga c = cargaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carga con id " + id + " no encontrado"));
        c.setViajeId(req.getViajeId());
        c.setCuentaId(req.getCuentaId());
        c.setMontoTotal(req.getMontoTotal());
        c.setCargaNormal(req.getCargaNormal());
        c.setCargaPausaExtendida(req.getCargaPausaExtendida());
        c.setDuracionMinutos(req.getDuracionMinutos());
        c.setDuracionPausaMinutos(req.getDuracionPausaMinutos());
        c.setKmRecorridos(req.getKmRecorridos());
        c.setFechaCarga(req.getFechaCarga());
        return this.toResponse(cargaRepository.save(c));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.cargaRepository.delete(
            this.cargaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carga con id " + id + " no encontrado"))
        );
    }

    private CargaResponse toResponse(Carga carga) {
        return new CargaResponse(
            carga.getViajeId(),
            carga.getCuentaId(),
            (carga.getDuracionMinutos() * carga.getCargaNormal())
           + (Math.max(0, carga.getDuracionPausaMinutos() - 15) * carga.getCargaPausaExtendida()),
            carga.getKmRecorridos(),
            carga.getFechaCarga()
        );
    }
}
