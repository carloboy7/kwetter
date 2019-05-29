package auth;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;

/**
 * Custom Realm class
 *
 * @author dgisbert
 */

public class MyCustomRealm extends AppservRealm
{
    /** JAAS Context parameter name */ public static final String PARAM_JAAS_CONTEXT = "jaas-context";

    /** Authentication type description */

    private static final String AUTH_TPYE = "Authentication done by checking user at table USERS on database";

    @Override
    public void init(Properties properties) throws BadRealmException, NoSuchRealmException
    {
        String propJaasContext = properties.getProperty(PARAM_JAAS_CONTEXT);

        if (propJaasContext != null) setProperty(PARAM_JAAS_CONTEXT, propJaasContext);

        Logger logger = Logger.getLogger(this.getClass().getName());

        String realmName = this.getClass().getSimpleName();

        logger.log(Level.INFO, realmName + " started. ");
        logger.log(Level.INFO, realmName + ": " + getAuthType());

        for (Entry<Object, Object> property:properties.entrySet()) logger.log(Level.INFO, property.getKey() + ": " + property.getValue());
    }

    @Override
    public String getAuthType()
    {
        return AUTH_TPYE;
    }

    @Override
    public Enumeration<?> getGroupNames(String usid) throws InvalidOperationException, NoSuchUserException
    {
        return (Enumeration<?>) Arrays.asList(new String[]{"user"});
    }

    @Override
    public String getJAASContext(){
        return "MyCustomRealm";
    }
}