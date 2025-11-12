package microservicio.viaje.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import microservicio.viaje.dto.viaje.ViajeRequest;
import microservicio.viaje.dto.viaje.ViajeResponse;
import microservicio.viaje.entity.Viaje;
import microservicio.viaje.repository.ViajeRepository;

@Service("ViajeService")
public class ViajeService implements BaseService<ViajeRequest, ViajeResponse> {
    @Autowired
    private ViajeRepository viajeRepository;

    @Override
    @Transactional
    public List<ViajeResponse> findAll() {
        return this.viajeRepository.findAll().stream().map(this::castResponse).toList();
    }

    @Override
    @Transactional
    public ViajeResponse findById(Long id) {
        return this.viajeRepository.findById(id)
                .map(this::castResponse)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public ViajeResponse save(ViajeRequest request) {
        Viaje viaje = new Viaje();
        viaje.setIdUsuario(request.getUsuarioId());
        viaje.setIdCuenta(request.getCuentaId());
        viaje.setIdMonopatin(request.getMonopatinId());
        viaje.setFechaInicio(request.getInicio());
        viaje.setFechaFin(request.getFin());
        viaje.setUbicacionInicio(request.getOrigen());
        viaje.setUbicacionFin(request.getDestino());
        viaje.setDistanciaRecorrida(request.getDistanciaRecorrida());
        return this.castResponse(this.viajeRepository.save(viaje));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.viajeRepository.delete(this.viajeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se pudo eliminar el viaje con ID:" + id)));
    }

    @Override
    @Transactional
    public ViajeResponse update(Long id, ViajeRequest request) {
        Viaje viaje = this.viajeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro el viaje con ID: " + id));
        viaje.setFechaFin(request.getFin());
        viaje.setUbicacionFin(request.getDestino());
        viaje.setDistanciaRecorrida(request.getDistanciaRecorrida());
        return this.castResponse(this.viajeRepository.save(viaje));
    }

    private ViajeResponse castResponse(Viaje viaje) {
        return new ViajeResponse(
            viaje.getIdUsuario(),
            viaje.getIdCuenta(),
            viaje.getIdMonopatin(),
            viaje.getFechaInicio(),
            viaje.getFechaFin(),
            Duration.between(viaje.getFechaInicio(), viaje.getFechaFin()).getSeconds(),
            viaje.getDistanciaRecorrida(),
            viaje.getEstado()
        );
    }
}
