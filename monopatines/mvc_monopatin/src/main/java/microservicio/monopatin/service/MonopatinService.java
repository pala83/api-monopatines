package microservicio.monopatin.service;


import microservicio.monopatin.dto.monopatin.MonopatinRequest;
import microservicio.monopatin.dto.monopatin.MonopatinResponse;
import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Monopatin;
import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.repository.MonopatinRepository;
import microservicio.monopatin.repository.ParadaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service("MonopatinService")
public class  MonopatinService implements BaseService<MonopatinRequest, MonopatinResponse> {

    @Autowired
    private MonopatinRepository monopatinRepository;
    @Autowired
    private ParadaRepository paradaRepository;

    @Override
    @Transactional
    public List<MonopatinResponse> findAll() {
        return monopatinRepository
            .findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }
    @Override
    @Transactional
    public MonopatinResponse findById(Long id) {
        return toResponse(
            this.monopatinRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Monopatin con id " + id + " no encontrado")));
    }

    @Override
    @Transactional
    public MonopatinResponse save(MonopatinRequest request) {
        Monopatin m = new Monopatin();
        m.setMarca(request.getMarca());
        m.setCodigoQR(request.getCodigoQR());
        m.setKmTotales(request.getKmTotales());
        m.setEstado(request.getEstado());
        m.setUbicacionActual(request.getUbicacionActual());
        if (request.getParadaActualId() != null) {
            Parada p = paradaRepository.findById(request.getParadaActualId()).orElseThrow(() -> new EntityNotFoundException("Parada con id " + request.getParadaActualId() + " no existe"));
            m.setParadaActual(p);
        }
        return this.toResponse(monopatinRepository.save(m));
    }

    @Override
    @Transactional
    public MonopatinResponse update(Long id, MonopatinRequest req) {
        Monopatin m = monopatinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Monopatin con id " + id + " no encontrado"));
        m.setMarca(req.getMarca());
        m.setCodigoQR(req.getCodigoQR());
        m.setKmTotales(req.getKmTotales());
        m.setEstado(req.getEstado());
        m.setUbicacionActual(req.getUbicacionActual());
        if (req.getParadaActualId() != null) {
            Parada p = paradaRepository.findById(req.getParadaActualId()).orElseThrow(() -> new EntityNotFoundException("Parada con id " + req.getParadaActualId() + " no existe"));
            m.setParadaActual(p);
        } else {
            m.setParadaActual(null);
        }
        return this.toResponse(monopatinRepository.save(m));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.monopatinRepository.delete(
            this.monopatinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Monopatin con id " + id + " no encontrado"))
        );
    }

    @Transactional
    public List<MonopatinResponse> listarDisponibles() {
        return monopatinRepository
            .findByEstado(EstadoMonopatin.DISPONIBLE)
            .stream()
            .map(this::toResponse)
            .toList();
    }

//comentado porque rompetodo la query hay que arreglarla
//    @Transactional
//    public List<MonopatinResponse> listarPorCercania(Double lat, Double lon, int limite) {
//        return monopatinRepository.buscarPorCercania(lat, lon, limite)
//                .stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }

    private MonopatinResponse toResponse(Monopatin m) {
        MonopatinResponse r = new MonopatinResponse();
        r.setId(m.getId());
        r.setMarca(m.getMarca());
        r.setCodigoQR(m.getCodigoQR());
        r.setKmTotales(m.getKmTotales());
        r.setEstado(m.getEstado());
        r.setUbicacionActual(
            m.getUbicacionActual().getLatitud() + ", " + m.getUbicacionActual().getLongitud()
        );
        if (m.getParadaActual() != null) {
            r.setParadaActualId(m.getParadaActual().getId());
            r.setParadaActualNombre(m.getParadaActual().getNombre());
        }
        return r;
    }
}
