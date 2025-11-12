package microservicio.facturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import microservicio.facturacion.dto.tarifa.TarifaRequest;
import microservicio.facturacion.dto.tarifa.TarifaResponse;
import microservicio.facturacion.entity.Tarifa;
import microservicio.facturacion.repository.TarifaRepository;

@Service("TarifaService")
public class TarifaService implements BaseService<TarifaRequest, TarifaResponse> {
    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    @Transactional
    public List<TarifaResponse> findAll() {
        return tarifaRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public TarifaResponse findById(Long id) {
        return toResponse(
            this.tarifaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarifa con id " + id + " no encontrado")));
    }

    @Override
    @Transactional
    public TarifaResponse save(TarifaRequest request) {
        Tarifa tarifa = new Tarifa();
        tarifa.setPrecioPorMinuto(request.getPrecioPorMinuto());
        tarifa.setPrecioPorMinutoExtendido(request.getPrecioPorMinutoExtendido());
        tarifa.setMensualidadPremium(request.getMensualidadPremium());
        return this.toResponse(tarifaRepository.save(tarifa));
    }

    @Override
    @Transactional
    public TarifaResponse update(Long id, TarifaRequest req) {
        Tarifa t = tarifaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarifa con id " + id + " no encontrado"));
        t.setPrecioPorMinuto(req.getPrecioPorMinuto());
        t.setPrecioPorMinutoExtendido(req.getPrecioPorMinutoExtendido());
        t.setMensualidadPremium(req.getMensualidadPremium());
        return this.toResponse(tarifaRepository.save(t));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.tarifaRepository.delete(
            this.tarifaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarifa con id " + id + " no encontrado"))
        );
    }

    private TarifaResponse toResponse(Tarifa tarifa) {
        return new TarifaResponse(
            tarifa.getId(),
            tarifa.getPrecioPorMinuto(),
            tarifa.getPrecioPorMinutoExtendido(),
            tarifa.getMensualidadPremium()
        );
    }
}
