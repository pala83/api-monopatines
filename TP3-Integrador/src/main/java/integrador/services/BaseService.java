package integrador.services;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll() throws Exception;
    T findById(Long id) throws Exception;
    T save(Object entity) throws Exception;
    T update(Long id, Object entity) throws Exception;
    boolean delete(Long id) throws Exception;    
}