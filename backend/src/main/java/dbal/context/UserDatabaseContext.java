package dbal.context;

import entities.user.User;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Default
@ApplicationScoped
@Singleton
public class UserDatabaseContext extends SimpleDatabaseContext<User> implements UserContext {


    @Override
    public String getPrefix() {
        return "user.";
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<User> findBy(String field, Object value) {
        switch (field){
            case "name" :
                return this.em.createNamedQuery("user.findByName").setParameter("name", value).getResultList();
            case "likeintext":
                return em.createNamedQuery(getPrefix() + "likeintext").setParameter("query", value).getResultList();

        }
        return Collections.emptyList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void deleteFollower(int follower, int following) {
        em.createNamedQuery("user.deleteFollower").setParameter(1, follower).setParameter(2, following).executeUpdate();
    }
}
