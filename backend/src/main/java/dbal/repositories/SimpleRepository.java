package dbal.repositories;

import dbal.context.BaseContext;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SimpleRepository<E, T extends BaseContext<E>> {
    protected final T context;

    public SimpleRepository(T context){
        this.context = context;
    }

    public List<E> findAll(){
        return context.findAll();
    }

    public E findById(@Min(1) int id){
        return context.findById(id);
    }
    public List<E> findBy(String field, Object value){
        return context.findBy(field, value);
    }
    public void create(@NotNull E entity){
        context.create(entity);
    }
    public void update(@NotNull E entity) throws EntityNotFoundException {
        context.update(entity);
    }
    public void delete(@NotNull E entity){
        context.delete(entity);
    }

    public void refresh(E entity) {
        context.refresh(entity);
    }
}
