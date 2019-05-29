package dbal.repositories;

import dbal.context.BaseContext;
import dbal.context.UserContext;
import entities.user.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.ejb.Singleton;
import java.util.List;

@Stateless
public class UserRepository extends SimpleRepository<User,BaseContext<User>> {

    @Inject
    public UserRepository(UserContext context) {
        super(context);
    }
    public UserRepository(){
        super(null);
    }
    public List<User> LikeInName(String query){
        return context.findBy("likeintext",query);
    }
    public void deleteFollower(int follower, int following) {
        ((UserContext) context).deleteFollower(follower, following);
    }
}
