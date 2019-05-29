package services;

import dbal.repositories.KweetRepository;
import dbal.repositories.SimpleRepository;
import entities.kweet.Kweet;
import entities.user.User;
import interceptors.KweetCreateInterceptor;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
@Dependent
public class KweetService extends SimpleService<Kweet>{

    @Inject
    public KweetService( KweetRepository repository) {
        super(repository);
    }
    public KweetService() {
        super(null);
    }
    public List<Kweet> getAllKweetsFromUser(@NotNull User user){
        return repository.findBy("user", user);
    }
    public List<Kweet> searchKweetsFor(@NotNull String search){
        return ((KweetRepository) repository).likeInText(search);
    }

    @Override
    @Interceptors(KweetCreateInterceptor.class)
    public void create(Kweet kweet) {
        super.create(kweet);
    }
}
