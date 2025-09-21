package org.db.dao;

import java.util.List;

public interface DAO<T> {
    int insert(T dao) throws Exception;
    int insertBatch(List<T> daoList) throws Exception;
    boolean delete(Integer id);
    T find(Integer pk);
    boolean update(T dao);
    List<T> selectAll();
}