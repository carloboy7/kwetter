package jaxrs.converters;

import jaxrs.factories.KweetFactory;
import shared.restModels.Kweet;
import services.KweetService;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;

@Default
@Dependent
public class KweetParamConverter implements ParamConverter<Kweet> {

    @Inject
    KweetService kweetService;

    @Override
    public Kweet fromString(String s){
        try{
            int result = Integer.parseInt(s);
            if(result <= 0){
                throw new NumberFormatException();
            }
            return KweetFactory.createKweetFromEntity(kweetService.getById(result));
        }catch (Exception e){
             throw new NotFoundException();
        }
    }


    @Override
    public String toString(Kweet mo){
        return mo.toString();
    }

}