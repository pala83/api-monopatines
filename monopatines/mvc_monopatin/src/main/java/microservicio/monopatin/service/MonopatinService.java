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

    @Transactional
    public MonopatinResponse actualizarEstado(Long id, EstadoMonopatin nuevoEstado) {
        Monopatin m = monopatinRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Monopatin con id " + id + " no encontrado"));
        m.setEstado(nuevoEstado);
        return toResponse(monopatinRepository.save(m));
    }

    @Transactional
    public MonopatinResponse ubicarEnParada(Long monopatinId, Long paradaId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
            .orElseThrow(() -> new EntityNotFoundException("Monopatín con ID " + monopatinId + " no encontrado"));        
        Parada parada = paradaRepository.findById(paradaId)
            .orElseThrow(() -> new EntityNotFoundException("Parada con ID " + paradaId + " no encontrada"));
        
        if (monopatin.getEstado() == EstadoMonopatin.EN_USO) {
            throw new IllegalStateException("No se puede ubicar el monopatín en una parada mientras está en uso");
        }
        
        if (monopatin.getEstado() == EstadoMonopatin.MANTENIMIENTO) {
            throw new IllegalStateException("No se puede ubicar el monopatín en una parada mientras está en mantenimiento");
        }
        
        long monopatinesEnParada = monopatinRepository.countByParadaActual(parada);
        if (monopatinesEnParada >= parada.getCapacidad()) {
            throw new IllegalStateException("La parada '" + parada.getNombre() + "' ha alcanzado su capacidad máxima (" + parada.getCapacidad() + ")");
        }
        
        // Actualizar ubicación del monopatín
        monopatin.setParadaActual(parada);
        monopatin.setUbicacionActual(parada.getUbicacion());
        
        // Agregar el monopatín a la lista de monopatines de la parada
        if (!parada.getMonopatines().contains(monopatin)) {
            parada.agregarMonopatin(monopatin);
        }
        
        // Si estaba en otra ubicación y está disponible, mantener su estado
        // TODO: Preguntar en caso de que esté en mantenimiento
        if (monopatin.getEstado() != EstadoMonopatin.DISPONIBLE) {
            monopatin.setEstado(EstadoMonopatin.DISPONIBLE);
        }
        
        return toResponse(monopatinRepository.save(monopatin));
    }

    @Transactional
    public MonopatinResponse retirarDeParada(Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
            .orElseThrow(() -> new EntityNotFoundException("Monopatín con ID " + monopatinId + " no encontrado"));
        
        if (monopatin.getParadaActual() == null) {
            throw new IllegalStateException("El monopatín no está ubicado en ninguna parada");
        }
        
        // Se entiende que el monopatin ahhora anda suelto, rodando por la carretera
        monopatin.setParadaActual(null);
        
        return toResponse(monopatinRepository.save(monopatin));
    }

    @Transactional(readOnly = true)
    public boolean verificarMonopatinEnParada(Long paradaId, Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
            .orElseThrow(() -> new EntityNotFoundException("Monopatín con ID " + monopatinId + " no encontrado"));
        return (monopatin.getParadaActual() != null && monopatin.getParadaActual().getId().equals(paradaId));
    }

    private MonopatinResponse toResponse(Monopatin m) {
        MonopatinResponse r = new MonopatinResponse();
        r.setId(m.getId());
        r.setMarca(m.getMarca());
        r.setCodigoQR(m.getCodigoQR());
        r.setKmTotales(m.getKmTotales());
        r.setEstado(m.getEstado());
        if (m.getUbicacionActual() != null) {
            r.setUbicacionActual(
                m.getUbicacionActual().getLatitud() + ", " + m.getUbicacionActual().getLongitud()
            );
        }
        if (m.getParadaActual() != null) {
            r.setParadaActualId(m.getParadaActual().getId());
            r.setParadaActualNombre(m.getParadaActual().getNombre());
        }
        return r;
    }
}
