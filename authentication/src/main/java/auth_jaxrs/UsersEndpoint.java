package auth_jaxrs;

import models.Role;
import models.User;
import rest_models.UserRegistration;
import services.RoleService;
import services.UserService;
import shared.restModels.ProfileImage;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Path(value = "/users")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsersEndpoint extends UserInformationProvider {

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Path("/search")
    @GET
    public Object search(@QueryParam("query") String query) throws IOException, URISyntaxException {
        return backendEndpoint.search(query);
    }

    @Path("/{id}/kweets")
    @GET
    public Object kweets(@PathParam("id") int id) throws IOException {
        return super.kweets(id);
    }

    @GET
    @Path("/{id}/followers")
    public Object followers(@PathParam("id") int id ) throws IOException {
        return super.followers(id);
    }

    @GET
    @Path("/{id}/following")
    public Object following( @PathParam("id") int id) throws IOException {
        return super.following(id);
    }

    @GET
    @Path("/{id}")
    public Object singleUser(@PathParam("id") int id) throws IOException {
        return super.singleUser(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Object createUser(@Valid UserRegistration user) throws IOException {

        User entity = userService.getByName(user.getEmail());
        if(entity != null){
            return "already";
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRole("user"));

        userService.createNewUser(roles,user.getEmail(), user.getPassword());

        entity = userService.getByName(user.getEmail());

        shared.restModels.User rest = new shared.restModels.User();
        rest.setName(user.getName());
        rest.setId(entity.getId());

        ProfileImage profileImage = new ProfileImage();
        if(user.getProfileImageId() > 0 && user.getProfileImageId() < 13) {
            profileImage.setUrl("../../assets/src/avatars/"+user.getProfileImageId()+".svg");
        }


        rest.setProfileImage(profileImage);
        return backendEndpoint.createUser(rest);
    }

}
