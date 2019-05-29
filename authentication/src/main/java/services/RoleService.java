package services;

import models.Role;
import models.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

@Stateless
public class RoleService {


    @PersistenceContext()
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public void create(Role role){

        em.persist(role);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Role getRole(String name){
       return (Role) em.createNamedQuery("Role.name").setParameter("name", name).getSingleResult();
    }
}
