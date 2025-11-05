package microservicio.usuario.service;

import java.util.List;

public interface BaseService<Request, Response> {
    List<Response> findAll();
    Response findById(Long id);
    Response save(Request entity);
    // Response update(Long id, Request entity);
    boolean delete(Long id);    
}
