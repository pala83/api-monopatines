package practico.integrador.service;


import lombok.RequiredArgsConstructor;
import practico.integrador.dto.monopatin.*;
import practico.integrador.entity.*;
import practico.integrador.repository.*;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonopatinService {

    private final MonopatinRepository monopatinRepository;
    private final ParadaRepository paradaRepository;

    public List<MonopatinResponse> findAll() {
        return monopatinRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public MonopatinResponse findById(Long id) {
        Monopatin m = monopatinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Monopatin no encontrado"));
        return toResponse(m);
    }

    public MonopatinResponse create(MonopatinRequest req) {
        Monopatin m = new Monopatin();
        m.setCodigoQR(req.getCodigoQR());
        m.setKmTotales(req.getKmTotales());
        m.setEstado(EstadoMonopatin.valueOf(req.getEstado()));
        if (req.getParadaActualId() != null) {
            Parada p = paradaRepository.findById(req.getParadaActualId()).orElseThrow(() -> new EntityNotFoundException("Parada no existe"));
            m.setParadaActual(p);
        }
        Monopatin saved = monopatinRepository.save(m);
        return toResponse(saved);
    }

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
        return toResponse(monopatinRepository.save(m));
    }

    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    public List<MonopatinResponse> listDisponibles() {
        return monopatinRepository.findByEstado(EstadoMonopatin.DISPONIBLE).stream().map(this::toResponse).collect(Collectors.toList());
    }

    /**
     * Lista monopatines ordenados por distancia euclidiana simple respecto a lat/lon pasados.
     * NOTA: cálculo simple en memoria (para la 1ra entrega está bien). En prod debería usarse index espacial.
     */
    public List<MonopatinResponse> listByProximity(Double lat, Double lon, int limit) {
        List<Monopatin> all = monopatinRepository.findAll();
        return all.stream()
                .filter(m -> m.getParadaActual() != null)
                .sorted(Comparator.comparingDouble(m -> distanceSquared
                        (lat, lon, m.getParadaActual().getLatitud(), m.getParadaActual().getLongitud())))
                .limit(limit <= 0 ? 10 : limit)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private double distanceSquared(double lat1, double lon1, double lat2, double lon2) {
        double dx = lat1 - lat2;
        double dy = lon1 - lon2;
        return dx * dx + dy * dy;
    }

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
