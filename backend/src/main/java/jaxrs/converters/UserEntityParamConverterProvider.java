package jaxrs.converters;

import entities.user.User;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Stateless
@Provider
@Dependent
public class UserEntityParamConverterProvider implements ParamConverterProvider{

    @Inject
    UserEntityParamConverter userParamConverter;

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(rawType.equals(User.class)){
            return (ParamConverter<T>) userParamConverter;
        }
        return null;
    }
}