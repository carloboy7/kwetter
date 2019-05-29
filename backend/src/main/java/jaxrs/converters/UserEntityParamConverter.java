package jaxrs.converters;

import entities.user.User;
import services.UserService;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;
import java.util.logging.Logger;

@Default
@Dependent
public class UserEntityParamConverter implements ParamConverter<User> {

    private static final Logger LOG = Logger.getLogger(UserEntityParamConverter.class.getName());


    @Inject
    private UserService userService;


    @Override
    public User fromString(String s){
        try{
            int result = Integer.parseInt(s);
            if(result <= 0){
                throw new NumberFormatException();
            }
            entities.user.User entity = userService.getById(result);
            if(entity == null){
                throw new NotFoundException("Entity is null");
            }
            return entity;
        }catch (Exception e){
            throw new NotFoundException();
        }
    }


    @Override
    public String toString(User mo){
        return mo.toString();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}