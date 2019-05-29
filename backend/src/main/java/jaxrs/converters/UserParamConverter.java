package jaxrs.converters;

import jaxrs.factories.UserFactory;
import shared.restModels.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;

@Default
@Dependent
public class UserParamConverter implements ParamConverter<User> {

    @Inject
    private UserEntityParamConverter userEntityParamConverter;


    @Override
    public User fromString(String s){
        try{
            return UserFactory.createUserFromEntity(userEntityParamConverter.fromString(s));
        }catch (Exception e){
             throw new NotFoundException();
        }
    }


    @Override
    public String toString(User mo){
        return mo.toString();
    }

}