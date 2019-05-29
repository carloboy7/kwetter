package dbal.context;

import entities.kweet.Kweet;
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
public class KweetDatabaseContext extends SimpleDatabaseContext<Kweet> implements KweetContext {
    @Override
    public String getPrefix() {
        return "kweet.";
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<Kweet> findBy(String field, Object value) {
        switch (field){
            case "user":
                return em.createNamedQuery(getPrefix() + "findByUser").setParameter("user", value).getResultList();
            case "likeintext":
                return em.createNamedQuery(getPrefix() + "likeintext").setParameter("query", value).getResultList();

        }
        return Collections.emptyList();

    }
}
