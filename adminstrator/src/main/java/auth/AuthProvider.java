package auth;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;

public class AuthProvider implements shared.communication.AuthProvider {
    @Override
    public String getAuthHeader() {
        Subject subject = null;
        try {
            subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
        } catch (PolicyContextException e) {
            return "";
        }
        return subject.getPublicCredentials().toArray()[0].toString();
    }
}
