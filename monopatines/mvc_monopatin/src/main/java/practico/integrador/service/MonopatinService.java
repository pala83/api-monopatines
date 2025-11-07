package practico.integrador.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import practico.integrador.dto.monopatin.*;
import practico.integrador.entity.*;
import practico.integrador.repository.*;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class  MonopatinService implements BaseService<MonopatinRequest,MonopatinResponse> {

    private final MonopatinRepository monopatinRepository;
    private final ParadaRepository paradaRepository;

    @Override
    @Transactional
    public List<MonopatinResponse> findAll() {
        return monopatinRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public MonopatinResponse findById(Long id) {
        Monopatin m = monopatinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Monopatin no encontrado"));
        return toResponse(m);
    }

    @Override
    @Transactional
    public MonopatinResponse save(MonopatinRequest request) {
        Monopatin m = new Monopatin();
        m.setCodigoQR(request.getCodigoQR());
        m.setKmTotales(request.getKmTotales());
        m.setEstado(EstadoMonopatin.valueOf(request.getEstado()));
        if (request.getParadaActualId() != null) {
            Parada p = paradaRepository.findById(request.getParadaActualId()).orElseThrow(() -> new EntityNotFoundException("Parada no existe"));
            m.setParadaActual(p);
        }
        return this.toResponse(monopatinRepository.save(m));
    }

    @Override
    @Transactional
    public MonopatinResponse update(Long id, MonopatinRequest req) {
        Monopatin m = monopatinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Monopatin no encontrado"));
        m.setCodigoQR(req.getCodigoQR());
        m.setKmTotales(req.getKmTotales());
        m.setEstado(EstadoMonopatin.valueOf(req.getEstado()));
        if (req.getParadaActualId() != null) {
            Parada p = paradaRepository.findById(req.getParadaActualId()).orElseThrow(() -> new EntityNotFoundException("Parada no existe"));
            m.setParadaActual(p);
        } else {
            m.setParadaActual(null);
        }
        return this.toResponse(monopatinRepository.save(m));
    }
    @Override
    @Transactional
    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    @Transactional
    public List<MonopatinResponse> listarDisponibles() {
        return monopatinRepository.findByEstado(EstadoMonopatin.DISPONIBLE).stream().map(this::toResponse).collect(Collectors.toList());
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
        r.setCodigoQR(m.getCodigoQR());
        r.setKmTotales(m.getKmTotales());
        r.setEstado(m.getEstado().name());
        if (m.getParadaActual() != null) {
            r.setParadaActualId(m.getParadaActual().getId());
            r.setParadaActualNombre(m.getParadaActual().getNombre());
        }
        return r;
    }
}
