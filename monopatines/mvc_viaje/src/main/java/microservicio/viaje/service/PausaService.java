package microservicio.viaje.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import microservicio.viaje.dto.pausa.PausaRequest;
import microservicio.viaje.dto.pausa.PausaResponse;
import microservicio.viaje.entity.Pausa;
import microservicio.viaje.repository.PausaRepository;
import microservicio.viaje.repository.ViajeRepository;

@Service("PausaService")
public class PausaService implements BaseService<PausaRequest, PausaResponse> {
    @Autowired
    private PausaRepository pausaRepository;
    @Autowired
    private ViajeRepository viajeRepository;

    @Override
    @Transactional
    public List<PausaResponse> findAll(){
        return this.pausaRepository.findAll().stream().map( this::castResponse ).toList();
    }

    @Override
    @Transactional
    public PausaResponse findById( Long id ){
        return this.pausaRepository.findById( id )
                .map( this::castResponse )
                .orElseThrow( () -> new RuntimeException("Pausa no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public PausaResponse save(PausaRequest request) {
        if(!viajeRepository.existsById(request.getViajeId())){
            throw new RuntimeException("No se puede crear la pausa porque el viaje no existe con ID: " + request.getViajeId());
        }
        Pausa pausa = new Pausa();
        pausa.setViaje(viajeRepository.findById(request.getViajeId()).get());
        pausa.setTiempoInicio(request.getTiempoInicio());
        pausa.setTiempoFin(request.getTiempoFin());
        pausa.setDuracionSegundos(request.getDuracionSegundos());
        pausa.setExtendido(request.getExtendido());
        return this.castResponse(this.pausaRepository.save(pausa));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.pausaRepository.delete(this.pausaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se pudo eliminar la pausa con ID:" + id)));
    }
    
    @Override
    @Transactional
    public PausaResponse update(Long id, PausaRequest request) {
        Pausa pausa = this.pausaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro la pausa con ID: " + id));
        if(!viajeRepository.existsById(request.getViajeId())){
            throw new RuntimeException("No se puede actualizar la pausa porque el viaje no existe con ID: " + request.getViajeId());
        }
        pausa.setViaje(viajeRepository.findById(request.getViajeId()).get());
        pausa.setTiempoInicio(request.getTiempoInicio());
        pausa.setTiempoFin(request.getTiempoFin());
        pausa.setDuracionSegundos(request.getDuracionSegundos());
        pausa.setExtendido(request.getExtendido());
        return this.castResponse(this.pausaRepository.save(pausa));
    }

    private PausaResponse castResponse(Pausa pausa) {
        return new PausaResponse(
            pausa.getViaje().getId(),
            pausa.getTiempoInicio(),
            pausa.getTiempoFin()
        );
    }
}
