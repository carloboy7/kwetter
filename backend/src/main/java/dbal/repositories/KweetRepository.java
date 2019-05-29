package dbal.repositories;

import dbal.context.BaseContext;
import dbal.context.KweetContext;
import dbal.context.UserContext;
import entities.kweet.Kweet;
import entities.user.User;
import interceptors.KweetCreateInterceptor;
import interceptors.UserCreateInterceptor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless
public class KweetRepository extends SimpleRepository<Kweet,BaseContext<Kweet>> {

    @Inject
    public KweetRepository(KweetContext context) {
        super(context);
    }
    public KweetRepository(){
        super(null);
    }

    public List<Kweet> likeInText(String search) {
        return ((KweetContext) context).findBy("likeintext",search);
    }
}
