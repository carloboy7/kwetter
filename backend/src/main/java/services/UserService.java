package services;

import dbal.repositories.KweetRepository;
import dbal.repositories.SimpleRepository;
import dbal.repositories.UserRepository;
import entities.user.User;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Dependent
public class UserService extends SimpleService<User>{

    @Inject
    public UserService(UserRepository repository) {
        super(repository);
    }
    public UserService(){
        super(null);
    }
    public boolean addFollowerToUser(@NotNull User original, @NotNull User follower){
        if(original.getId() == follower.getId()){
            return false;
        }
        original.addFollower(follower);
        this.update(original);

        this.refresh(this.getById(follower.getId()));

        return true;
    }
    public boolean removeFollowerFromUser(@NotNull User original, @NotNull User follower){
        if(original.getId() == follower.getId()){
            return false;
        }

        User newFollower = this.getById(follower.getId());
        User newOriginal = this.getById(original.getId());

        newOriginal.removeFollower(newFollower);
        newFollower.removeFollowing(newOriginal);


        ((UserRepository) repository).deleteFollower(follower.getId(), original.getId());

        this.refresh(newOriginal);
        this.refresh(newFollower);

        return true;
    }

    public List<User> searchUsersFor(String query) {
        return ((UserRepository) repository).LikeInName(query);
    }
}
