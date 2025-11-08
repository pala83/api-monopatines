package microservicio.facturacion.service;



import lombok.RequiredArgsConstructor;
import microservicio.facturacion.dto.tarifa.TarifaRequest;
import microservicio.facturacion.dto.tarifa.TarifaResponse;
import microservicio.facturacion.entity.Tarifa;
import microservicio.facturacion.repository.TarifaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TarifaService implements BaseService<TarifaRequest, TarifaResponse> {

    private final TarifaRepository tarifaRepository;

    private TarifaResponse toResponse(Tarifa t) {
        return new TarifaResponse(t.getId(), t.getPrecioPorMinuto(), t.getPrecioPausaPorMinuto(), t.getFechaInicioVigencia());
    }

    public List<TarifaResponse> findAll() {
        return tarifaRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TarifaResponse findById(Long id) {
        Tarifa t = tarifaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarifa no existe"));
        return toResponse(t);
    }

    @Transactional
    public TarifaResponse save(TarifaRequest req) {
        Tarifa t = new Tarifa();
        t.setPrecioPorMinuto(req.getPrecioPorMinuto());
        t.setPrecioPausaPorMinuto(req.getPrecioPausaPorMinuto());
        t.setFechaInicioVigencia(req.getFechaInicioVigencia());
        return toResponse(tarifaRepository.save(t));
    }

    @Transactional
    public TarifaResponse update(Long id, TarifaRequest req) {
        Tarifa t = tarifaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarifa no existe"));
        t.setPrecioPorMinuto(req.getPrecioPorMinuto());
        t.setPrecioPausaPorMinuto(req.getPrecioPausaPorMinuto());
        t.setFechaInicioVigencia(req.getFechaInicioVigencia());
        return toResponse(tarifaRepository.save(t));
    }

    @Transactional
    public void delete(Long id) {
        tarifaRepository.deleteById(id);
    }

    public TarifaResponse tarifaVigente(LocalDateTime fecha) {
        List<Tarifa> vigentes = tarifaRepository.findVigentesHasta(fecha);
        if (vigentes.isEmpty()) throw new EntityNotFoundException("No hay tarifa vigente");
        return toResponse(vigentes.getFirst());
    }
}

