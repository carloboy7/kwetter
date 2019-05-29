package jaxrs.factories;

import shared.restModels.Kweet;

public class KweetFactory {
    public static Kweet createKweetFromEntity(entities.kweet.Kweet entity){
        Kweet kweet = new Kweet();
        kweet.setId(entity.getId());
        kweet.setText(entity.getText());
        kweet.setUser(UserFactory.createUserFromEntity(entity.getUser()));
        return kweet;
    }

    public static entities.kweet.Kweet createEntityFromKweet(Kweet kweet){
        entities.kweet.Kweet entity = new entities.kweet.Kweet();
        if(entity.getUser() != null) {
            entity.setUser(UserFactory.createEntityFromUser(kweet.getUser()));
        }
        entity.setText(kweet.getText());
        return entity;
    }
}
