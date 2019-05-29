package auth;

import javax.security.auth.login.LoginException;

import auth.principal.CustomPrincipal;
import com.sun.appserv.security.AppservPasswordLoginModule;
import shared.communication.AuthProvider;

import java.security.Principal;

/**
 * Custom module
 *
 * @author dgisbert
 */

public class MyCustomModule extends AppservPasswordLoginModule
{
    private AuthProvider authProvider;

    @Override
    protected void authenticateUser() throws LoginException
    {
        UserAuthenticationService authService = new UserAuthenticationService();
        try {
            authService.validatePassword(_username, new String(_passwd));
            SecurityUtil.authenticateUser(_username, _passwd);
            commitUserAuthentication(authService.getGroups().toArray(new String[0]));
            authProvider = authService;
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }

    }

    @Override
    public boolean commit() throws LoginException {
        if(super.commit()){
           _subject.getPublicCredentials().add(authProvider.getAuthHeader());
           return true;
        }
        return false;
    }
}