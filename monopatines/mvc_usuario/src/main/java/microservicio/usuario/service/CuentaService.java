package microservicio.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import microservicio.usuario.dto.cuenta.CuentaRequest;
import microservicio.usuario.dto.cuenta.CuentaResponse;
import microservicio.usuario.entity.Cuenta;
import microservicio.usuario.repository.CuentaRepository;
import microservicio.usuario.service.exception.NotFoundException;

@Service("CuentaService")
public class CuentaService implements BaseService<CuentaRequest, CuentaResponse> {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    @Transactional
    public List<CuentaResponse> findAll(){
        return this.cuentaRepository.findAll().stream().map( this::castResponse ).toList();
    }

    @Override
    @Transactional
    public CuentaResponse findById( Long id ){
        return this.cuentaRepository.findById( id )
                .map( this::castResponse )
                .orElseThrow( () -> new NotFoundException("cuenta", id));
    }

    @Override
    @Transactional
    public CuentaResponse save(CuentaRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setFechaCreacion(request.getFechaCreacion());
        cuenta.setSaldo(request.getSaldo());
        cuenta.setActiva(request.isActiva());
        cuenta.setTipo(request.getTipo());
        return this.castResponse(this.cuentaRepository.save(cuenta));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.cuentaRepository.delete(this.cuentaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No se pudo eliminar la cuenta con ID:" + id)));
    }

    @Override
    @Transactional
    public CuentaResponse update(Long id, CuentaRequest request) {
        Cuenta cuenta = this.cuentaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No se encontro la cuenta con ID: " + id));
        cuenta.setSaldo(request.getSaldo());
        cuenta.setFechaCreacion(request.getFechaCreacion());
        cuenta.setActiva(request.isActiva());
        cuenta.setTipo(request.getTipo());
        return this.castResponse(cuentaRepository.save(cuenta));
    }


    @Transactional
    public CuentaResponse updateActivacionCuenta(Long id, boolean alta) {
        Cuenta cuenta = this.cuentaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No se encontró la cuenta con ID: " + id));
        cuenta.setActiva(alta);
        return this.castResponse(this.cuentaRepository.save(cuenta));
    }

    private CuentaResponse castResponse(Cuenta cuenta){
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getSaldo(),
                cuenta.isActiva(),
                cuenta.getFechaCreacion(),
                cuenta.getTipo()
        );
    }
}
