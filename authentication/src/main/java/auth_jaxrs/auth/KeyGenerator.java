package auth_jaxrs.auth;

import java.security.Key;

public interface KeyGenerator {

    Key generateKey();
}