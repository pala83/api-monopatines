package microservicio.mantenimiento.service;

import java.time.LocalDateTime;
import java.util.List;

import microservicio.mantenimiento.dto.feignClient.EstadoMonopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import microservicio.mantenimiento.client.MonopatinClient;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoRequest;
import microservicio.mantenimiento.dto.registroMantenimiento.RegistroMantenimientoResponse;
import microservicio.mantenimiento.entity.RegistroMantenimiento;
import microservicio.mantenimiento.repository.RegistroMantenimientoRepository;

@Service("registroMantenimientoService")
public class RegistroMantenimientoService implements BaseService<RegistroMantenimientoRequest, RegistroMantenimientoResponse> {

	@Autowired
	private RegistroMantenimientoRepository registroMantenimientoRepository;

	@Autowired
	private MonopatinClient monopatinClient;

	@Override
	@Transactional(readOnly = true)
	public List<RegistroMantenimientoResponse> findAll() {
		return registroMantenimientoRepository.findAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public RegistroMantenimientoResponse findById(Long id) {
		return registroMantenimientoRepository.findById(id)
				.map(this::toResponse)
				.orElse(null);
	}

	@Override
	@Transactional
	public RegistroMantenimientoResponse save(RegistroMantenimientoRequest req) {
		try {
			// Marcar el monopatín como en mantenimiento (no disponible)
			monopatinClient.actualizarEstado(req.getIdMonopatin(), EstadoMonopatin.MANTENIMIENTO);
		} catch (FeignException e) {
			if (e.status() == 404) {
				throw new EntityNotFoundException("El monopatín con ID " + req.getIdMonopatin() + " no existe");
			}
			throw new RuntimeException("Error al comunicarse con el servicio de monopatines: " + e.getMessage());
		}
		// Verificar que no haya un mantenimiento activo para ese monopatín
		List<RegistroMantenimiento> mantenimientosActivos = registroMantenimientoRepository
			.findByIdMonopatinAndFechaFinIsNull(req.getIdMonopatin());
		
		if (!mantenimientosActivos.isEmpty()) {
			throw new IllegalStateException("El monopatín con ID " + req.getIdMonopatin() + 
				" ya se encuentra en mantenimiento");
		}
		// Crear y guardar el registro de mantenimiento
		RegistroMantenimiento reg = new RegistroMantenimiento();
		reg.setIdMonopatin(req.getIdMonopatin());
		reg.setFechaInicio(LocalDateTime.now());
		RegistroMantenimiento saved = registroMantenimientoRepository.save(reg);

		return toResponse(saved);
	}

	@Override
	@Transactional
	public RegistroMantenimientoResponse update(Long id, RegistroMantenimientoRequest entity) {
		// TODO: implementar actualización si es necesario
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		registroMantenimientoRepository.deleteById(id);
	}

	@Transactional
	public RegistroMantenimientoResponse finalizarMantenimiento(Long id) {
		RegistroMantenimiento registro = registroMantenimientoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Registro de mantenimiento con ID " + id + " no encontrado"));
		
		if (registro.getFechaFin() != null) {
			throw new IllegalStateException("El registro de mantenimiento con ID " + id + " ya fue finalizado");
		}

		registro.setFechaFin(LocalDateTime.now());
		RegistroMantenimiento updated = registroMantenimientoRepository.save(registro);

		try {
			monopatinClient.actualizarEstado(registro.getIdMonopatin(), EstadoMonopatin.DISPONIBLE);
		} catch (FeignException e) {
			throw new RuntimeException("Error al actualizar el estado del monopatín: " + e.getMessage());
		}

		return toResponse(updated);
	}

	@Transactional(readOnly = true)
	public List<RegistroMantenimientoResponse> findByIdMonopatin(Long idMonopatin) {
		return registroMantenimientoRepository.findByIdMonopatin(idMonopatin)
			.stream()
			.map(this::toResponse)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<RegistroMantenimientoResponse> findMantenimientosActivos(Long idMonopatin) {
		return registroMantenimientoRepository.findByIdMonopatinAndFechaFinIsNull(idMonopatin)
			.stream()
			.map(this::toResponse)
			.toList();
	}

	private RegistroMantenimientoResponse toResponse(RegistroMantenimiento reg) {
		RegistroMantenimientoResponse resp = new RegistroMantenimientoResponse();
		resp.setId(reg.getId());
		resp.setIdMonopatin(reg.getIdMonopatin());
		resp.setFechaInicio(reg.getFechaInicio());
		resp.setFechaFin(reg.getFechaFin());
		return resp;
	}
}
