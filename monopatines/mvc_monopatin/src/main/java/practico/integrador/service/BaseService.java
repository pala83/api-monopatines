package practico.integrador.service;

import org.springframework.transaction.annotation.Transactional;
import practico.integrador.dto.monopatin.MonopatinRequest;
import practico.integrador.dto.monopatin.MonopatinResponse;

import java.util.List;

public interface BaseService<Request, Response> {
    List<Response> findAll();
    Response findById(Long id);
    Response save(Request entity);
    Response update(Long id, Request entity);
    void delete(Long id);
}
