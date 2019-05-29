package jaxrs.factories;

import shared.restModels.User;

public class UserFactory {
    public static User createUserFromEntity(entities.user.User entity){
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setBio(entity.getBio());
        user.setCreated(entity.getCreatedOn());
        if(entity.getProfileImage() != null) {
            user.setProfileImage(ProfileImageFactory.profileImageFromEntity(entity.getProfileImage()));
        }
        return user;
    }

    public static entities.user.User createEntityFromUser(User user){
        entities.user.User entity = new entities.user.User();
        entity.setWebsite(user.getWebsite());
        entity.setBio(user.getBio());
        entity.setName(user.getName());
        if(user.getProfileImage() != null) {
            entity.setProfileImage(ProfileImageFactory.createEntityFromProfileImage(user.getProfileImage()));
        }
        return entity;
    }
}
