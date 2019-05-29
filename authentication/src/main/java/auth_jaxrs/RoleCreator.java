package auth_jaxrs;

import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class RoleCreator {
    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @PostConstruct
    public void init(){
        createRole("moderator");
        createRole("user");
        createRole("admin");
    }

    private void createRole(String roleName) {
        Role moderator = new Role();
        moderator.setName(roleName);
        roleService.create(moderator);
    }
}
