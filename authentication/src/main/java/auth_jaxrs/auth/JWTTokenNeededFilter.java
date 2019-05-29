package auth_jaxrs.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import models.Role;
import models.User;
import services.UserService;

import javax.annotation.Priority;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
@Stateless
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);


        try {
            if(authorizationHeader == null){
                throw new Exception();
            }
            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();
            // Validate the token
            Key key = keyGenerator.generateKey();
            final Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            Logger.getAnonymousLogger().info("#### valid token : " + token);
            final SecurityContext originalContext = requestContext.getSecurityContext();
            User user = userService.getByName(claims.getSubject());
            if(user == null){
                throw new Exception();
            }
            final Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return claims::getSubject;
                }

                @Override
                public boolean isUserInRole(String s) {
                    return roles.contains(s);
                }

                @Override
                public boolean isSecure() {
                    return originalContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return originalContext.getAuthenticationScheme();
                }
            });



        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
