package dbal.context;

import entities.IdProvider;

import javax.enterprise.inject.Alternative;
import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleMemoryContext<E extends IdProvider> implements BaseContext<E> {

    private ArrayList<E> entities;

    public SimpleMemoryContext() {
        this.entities = new ArrayList<>();
    }

    @Override
    public List<E> findAll() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public E findById(int id) {
        return entities.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<E> findBy(String field, Object value) {
        if(entities.isEmpty()){
            return Collections.emptyList();
        }
        String methodName = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
        try {
            Method method = entities.get(0).getClass().getDeclaredMethod(methodName);
            return entities.stream().filter( e -> {
                try {
                    return method.invoke(e).equals(value);
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    return false;
                }
            }).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void create(E entity) {
        if(!entities.contains(entity)){
            try {
                Field idField = entity.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(entity, entities.size() + 1);
                entities.add(entity);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

        }
    }

    @Override
    public void update(E entity) throws EntityNotFoundException {
        E en = findById(entity.getId());
        if(en != null) {
            delete(en);
            entities.add(entity);
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public void delete(E entity){
        entities.remove(entity);
    }

    @Override
    public void refresh(E entity) {

    }
}
