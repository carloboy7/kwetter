package jaxrs.converters;

import shared.restModels.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Stateless
@Provider
public class UserParamConverterProvider implements ParamConverterProvider{

    @Inject
    UserParamConverter userParamConverter;

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(rawType.equals(User.class)){
            return (ParamConverter<T>) userParamConverter;
        }
        return null;
    }
}