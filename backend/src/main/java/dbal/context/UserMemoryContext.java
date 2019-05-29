package dbal.context;

import entities.user.User;

import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

@Alternative
@ApplicationScoped
public class UserMemoryContext extends SimpleMemoryContext<User> implements UserContext {
    @Override
    public void deleteFollower(int follower, int following) {
    }
}
