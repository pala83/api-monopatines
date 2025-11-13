package microservicio.mantenimiento.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		// 1) Guardar registro de mantenimiento con fecha de inicio
		RegistroMantenimiento reg = new RegistroMantenimiento();
		reg.setIdMonopatin(req.getIdMonopatin());
		reg.setFechaInicio(LocalDateTime.now());
		RegistroMantenimiento saved = registroMantenimientoRepository.save(reg);

	// 2) Marcar el monopatín como en mantenimiento (no disponible)
	monopatinClient.actualizarEstado(req.getIdMonopatin(), "MANTENIMIENTO");

		return toResponse(saved);
	}

	@Override
	@Transactional
	public RegistroMantenimientoResponse update(Long id, RegistroMantenimientoRequest entity) {
		// No hace falta para el alcance actual. Podría actualizar fecha fin, etc.
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		registroMantenimientoRepository.deleteById(id);
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
