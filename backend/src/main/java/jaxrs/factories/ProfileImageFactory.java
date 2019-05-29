package jaxrs.factories;

import shared.restModels.ProfileImage;

public class ProfileImageFactory {
    public static ProfileImage profileImageFromEntity(entities.user.ProfileImage entity){
        ProfileImage pi = new ProfileImage();
        pi.setUrl(entity.getLocation());
        return pi;
    }

    public static entities.user.ProfileImage createEntityFromProfileImage(ProfileImage image){
        entities.user.ProfileImage pi = new entities.user.ProfileImage();
        pi.setLocation(image.getUrl());
        return pi;
    }
}
