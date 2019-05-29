package auth_jaxrs.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.security.Key;

@Default
@ApplicationScoped
@Singleton
public class SimpleKeyGenerator implements KeyGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    private Key key;
    @Override
    public Key generateKey() {
        if(key == null){
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512); //or HS384 or HS512;
        }
        return key;
    }
}
