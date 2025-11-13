package microservicio.monopatin.service;

import microservicio.monopatin.dto.monopatin.MonopatinResponse;
import microservicio.monopatin.dto.parada.ParadaRequest;
import microservicio.monopatin.dto.parada.ParadaResponse;
import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.repository.ParadaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@Service("ParadaService")
public class ParadaService implements BaseService<ParadaRequest, ParadaResponse>{

    @Autowired
    private ParadaRepository paradaRepository;

    @Override
    @Transactional
    public List<ParadaResponse> findAll() {
        return paradaRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public ParadaResponse findById(Long id) {
        return toResponse(
            this.paradaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Parada con id " + id + " no encontrada")));
    }

    @Override
    @Transactional
    public ParadaResponse save(ParadaRequest req) {
        Parada p = new Parada();
        p.setNombre(req.getNombre());
        p.setUbicacion(req.getUbicacion());
        p.setCapacidad(req.getCapacidad());
        return toResponse(paradaRepository.save(p));
    }

    @Override
    @Transactional
    public ParadaResponse update(Long id, ParadaRequest req) {
        Parada p = paradaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Parada con id " + id + " no encontrada"));
        p.setNombre(req.getNombre());
        p.setUbicacion(req.getUbicacion());
        p.setCapacidad(req.getCapacidad());
        return toResponse(paradaRepository.save(p));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.paradaRepository.delete(
            this.paradaRepository
            .findById(id)
            .orElseThrow( () -> new EntityNotFoundException("Parada con id " + id + " no encontrada")));
    }

    private ParadaResponse toResponse(Parada p) {
        List<MonopatinResponse> monopatines = p.getMonopatines().stream().map(m ->
                new MonopatinResponse(m.getId(), m.getMarca(), m.getCodigoQR(), m.getKmTotales(), m.getUsoTotalMinutos(),
                        m.getEstado(),m.getFechaUltimoMantenimiento(),m.getUbicacionActual().getLatitud()+","+m.getUbicacionActual().getLongitud(),m.getParadaActual().getId(),
                        m.getParadaActual().getNombre())).toList();
        return new ParadaResponse(
            p.getNombre(), 
            p.getUbicacion().getLatitud() + "," + p.getUbicacion().getLongitud(),
            p.getCapacidad(),monopatines);
    }
}
