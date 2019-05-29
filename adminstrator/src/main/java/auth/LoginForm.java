package auth;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.Stateful;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Backing bean class for loginForm.xhtml page
 */

@Named
@SessionScoped
public class LoginForm implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String usid;
    private String pass;

    // Getters and setters

    public String getUsid()          { return usid;    }
    public String getPass()          { return pass;    }

    public void setUsid(String usid) { this.usid = usid; }
    public void setPass(String pass) { this.pass = pass; }

    //**************************************************
    // Public section
    //**************************************************

    /**
     * Do login on system
     */

    public Object doLogin()
    {
        String result = null;

        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try
        {
            Principal principal = request.getUserPrincipal();

            if (principal == null || !principal.getName().equals(usid)) request.login(this.usid, this.pass);

            result = "main";
        }
        catch (Exception e)
        {
            pass = null;

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }

        return result;
    }
}