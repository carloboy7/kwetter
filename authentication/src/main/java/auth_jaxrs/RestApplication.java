package auth_jaxrs;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(value = "/v1")
@DeclareRoles({"admin", "moderator", "user"})
public class RestApplication extends Application {
    public RestApplication() {
        super();

    }
}