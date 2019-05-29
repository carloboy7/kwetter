package services;

import com.google.common.hash.Hashing;
import models.Role;
import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext()
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public void createNewUser(List<Role> roles, String username, String password){
        User user = new User();
        user.setPassword(hashPassword(password));
        user.setUsername(username);
        user.setRoles(roles);
        em.persist(user);
    }

    private String hashPassword(String plainTextPassword){
        return Hashing.sha256().hashString(plainTextPassword, StandardCharsets.UTF_8).toString();
    }

    public User getByName(String name){
        try {
            return (User) em.createNamedQuery("SimpleUser.byName").setParameter("name", name).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    public boolean passwordCheck(User user, String password){
        return user.getPassword().equals(hashPassword(password));
    }
}
