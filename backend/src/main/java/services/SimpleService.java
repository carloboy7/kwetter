package services;

import dbal.repositories.SimpleRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SimpleService<T> {
    protected SimpleRepository<T, ?> repository;

    protected SimpleService( SimpleRepository<T, ?> repository) {
        this.repository = repository;
    }

    public void create(@Valid T user){
        repository.create(user);
    }
    public void update(@Valid T user){
        repository.update(user);
    }
    public void delete(@Valid T user){
        repository.delete(user);
    }
    public T getById(int id){
        return repository.findById(id);
    }
    public void refresh(T user){
        repository.refresh(user);
    }
}
