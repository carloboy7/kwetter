package factory;

import models.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
@Stateless
@Dependent
public class UserFactory {

    private static Logger LOG = Logger.getLogger(UserFactory.class.getName());


    public UserFactory(){}

    @Inject
    private UserService userService;

    public User createUserFromRequest(SecurityContext securityContext){
        Principal principal = securityContext.getUserPrincipal();
        if(principal == null){
            LOG.log(Level.WARNING, "principal is null");
            return null;
        } else {
            LOG.log(Level.INFO, "Create user from request :" + principal.getName());

        }

        return userService.getByName(principal.getName());
    }
}
