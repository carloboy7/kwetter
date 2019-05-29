package auth.principal;

import org.glassfish.security.common.PrincipalImpl;

public class CustomPrincipal extends PrincipalImpl {

    private String authHeader;

    public CustomPrincipal(String user) {
        super(user);
    }


    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }
}
