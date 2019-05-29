package dbal.context;

import java.util.List;

public interface BaseContext<T> {
    List<T> findAll();
    T findById(int id);
    List<T> findBy(String field, Object value);
    void create(T entity);
    void update(T entity);
    void delete(T entity);

    void refresh(T entity);
}
