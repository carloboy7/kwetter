package auth_jaxrs.auth;


import converter.RoleToSharedRole;
import factory.UserFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;
import services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path(value = "/auth")
@Produces(TEXT_PLAIN)
@Stateless
public class AuthEndpoint {

    @Inject
    private UserService userService;

    @Inject
    private UserFactory factory;

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @GET
    @Path("/login")
    public String getAuthHeader(@QueryParam("username") String username, @QueryParam("password") String password ){
        User user;
        try{
            user  = userService.getByName(username);
        }catch (Exception ignored){
            return "wrong";
        }
        if(user == null){
            return "wrong";
        }
        if(userService.passwordCheck(user, password)){
            return issueToken(user.getUsername());
        }
        return "wrong";
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        Logger.getAnonymousLogger().info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @GET
    @Path("/roles")
    @JWTTokenNeeded
    @Produces(APPLICATION_JSON)
    public Object getRoles( @Context SecurityContext securityContext) throws IOException {
        return factory
                .createUserFromRequest(securityContext)
                .getRoles()
                .stream()
                .map(RoleToSharedRole::toSharedRole)
                .collect(Collectors.toList());
    }
}
