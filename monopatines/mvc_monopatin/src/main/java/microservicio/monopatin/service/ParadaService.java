package microservicio.monopatin.service;



import lombok.RequiredArgsConstructor;
import microservicio.monopatin.dto.parada.ParadaRequest;
import microservicio.monopatin.dto.parada.ParadaResponse;
import microservicio.monopatin.dto.parada.*;
import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.repository.ParadaRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParadaService implements BaseService<ParadaRequest, ParadaResponse>{

    private final ParadaRepository paradaRepository;

    public List<ParadaResponse> findAll() {
        return paradaRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ParadaResponse findById(Long id) {
        Parada p = paradaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Parada no encontrada"));
        return toResponse(p);
    }

    public ParadaResponse save(ParadaRequest req) {
        Parada p = new Parada();
        p.setNombre(req.getNombre());
        p.setLatitud(req.getLatitud());
        p.setLongitud(req.getLongitud());
        return toResponse(paradaRepository.save(p));
    }

    public ParadaResponse update(Long id, ParadaRequest req) {
        Parada p = paradaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Parada no encontrada"));
        p.setNombre(req.getNombre());
        p.setLatitud(req.getLatitud());
        p.setLongitud(req.getLongitud());
        return toResponse(paradaRepository.save(p));
    }

    public void delete(Long id) {
        paradaRepository.deleteById(id);
    }

    private ParadaResponse toResponse(Parada p) {
        return new ParadaResponse(p.getId(), p.getNombre(), p.getLatitud(), p.getLongitud());
    }
}
