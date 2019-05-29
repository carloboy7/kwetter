package dbal.context;

import entities.IdProvider;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SimpleDatabaseContext<E extends IdProvider> implements BaseContext<E> {


    public abstract String getPrefix();


    @PersistenceContext
    protected EntityManager em;

    public SimpleDatabaseContext() {

    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<E> findAll() {

        return em.createNamedQuery(getPrefix() + "findAll").getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public E findById(int id) {

        return (E) em.createNamedQuery(getPrefix() + "findById").setParameter("id", id).getSingleResult();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void create(E entity) {
        em.persist(entity);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void update(E entity) throws EntityNotFoundException {
        if(findById(entity.getId()) == null) {
            throw new EntityNotFoundException();
        }
        em.merge(entity);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void delete(E entity){
        em.remove(entity);
    }

    @Override
    public void refresh(E entity) {
        em.refresh(entity);
    }
}
