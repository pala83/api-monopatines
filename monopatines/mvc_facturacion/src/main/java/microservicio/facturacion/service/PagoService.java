package microservicio.facturacion.service;

import lombok.RequiredArgsConstructor;
import microservicio.facturacion.dto.pago.PagoRequest;
import microservicio.facturacion.dto.pago.PagoResponse;
import microservicio.facturacion.entity.Pago;
import microservicio.facturacion.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PagoService implements BaseService<PagoRequest, PagoResponse> {

    private final PagoRepository pagoRepository;

    private PagoResponse toResponse(Pago p) {
        return new PagoResponse(p.getId(), p.getIdCuenta(), p.getMonto(), p.getFechaPago(), p.getMedio());
    }

    public List<PagoResponse> findAll() {
        return pagoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PagoResponse findById(Long id) {
        Pago p = pagoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pago no existe"));
        return toResponse(p);
    }


    @Transactional
    public PagoResponse save(PagoRequest req) {
        Pago p = new Pago();
        p.setIdCuenta(req.getIdCuenta());
        p.setMonto(req.getMonto());
        p.setFechaPago(req.getFechaPago());
        p.setMedio(req.getMedio());
        return toResponse(pagoRepository.save(p));
    }

    @Transactional
    public PagoResponse update(Long id, PagoRequest req) {
        Pago p = pagoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pago no existe"));
        p.setIdCuenta(req.getIdCuenta());
        p.setMonto(req.getMonto());
        p.setFechaPago(req.getFechaPago());
        p.setMedio(req.getMedio());
        return toResponse(pagoRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    public Double totalFacturadoEntre(LocalDateTime desde, LocalDateTime hasta) {
        Double suma = pagoRepository.totalBetweenDates(desde, hasta);
        return suma == null ? 0.0 : suma;
    }

    public List<PagoResponse> pagosPorCuenta(Long idCuenta) {
        return pagoRepository.findByIdCuenta(idCuenta).stream().map(this::toResponse).collect(Collectors.toList());
    }
}
