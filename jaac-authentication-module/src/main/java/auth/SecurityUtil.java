package auth;

import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

/**
 * Utility class that implements a lookup method to locate the authorization service class that allows to authenticate users.
 *
 * @author dgisbert
 */

class SecurityUtil
{
    private static Logger logger = Logger.getLogger(SecurityUtil.class.getName());

    public static final String AUTHENTICATION_INTERFACE = "java:global/com-booreg-war/UserAuthenticationService";

    /**
     * Returns the interface IUserValidationService to the class that will validate username and password.
     */

    private static IUserAuthenticationService lookupUserValidationService() throws Exception
    {


        return new UserAuthenticationService();
    }

    /**
     * Calls the validation service Valida l'usuari / contrassenya donat utilitzant el servei d'utenticació especificat en la classe. Aquest servei es troba en el servidor / port donat
     */

    public static void authenticateUser(String username, char[] password) throws LoginException
    {
        logger.log(Level.INFO, AUTHENTICATION_INTERFACE + " trying to authenticate user " + username);

        try
        {
            IUserAuthenticationService validationService = lookupUserValidationService();
            validationService.validatePassword(username, new String(password));
        }
        catch (LoginException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //TODO El missatge d'error hauria de dependre de l'idioma de l'usuari
            throw new LoginException((new StringBuilder()).append("Error en la validación del usuario: ").append(username).toString() + ". " + e.getMessage());
        }
    }

    /**
     * Returns the groups of this user
     */

    public static String[] getGroups(String usid)
    {
        List<String> result = new Vector<String>();

        logger.log(Level.INFO, AUTHENTICATION_INTERFACE + " trying to get groups of user  " + usid);

        try
        {
            IUserAuthenticationService validationService = SecurityUtil.lookupUserValidationService();
            result = validationService.getGroups();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result.toArray(new String[0]);
    }
}