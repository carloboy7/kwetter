package entities;

import entities.user.*;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class UserEntityTest {

    @Test
    void UserHasDefaultConstructor(){
        User user = new User();
        assertThat(user, not(nullValue()));
    }

    @Test
    void BasicBioUserCreation(){
        User user = new User();

        user.setBio("This bio should be saved with a user");
        assertThat(user.getBio(), is("This bio should be saved with a user"));
    }

    @Test
    void BasicNameUserCreation(){
        User user = new User();
        user.setName("Henk");
        assertThat(user.getName(), is("Henk"));
    }

    @Test
    void BasicWebsiteUserCreation(){
        User user = new User();


        user.setWebsite("http://hey.nl");
        assertThat(user.getWebsite(), is("http://hey.nl"));
    }

    @Test
    void BasicProfilePictureUserCreation(){
        User user = new User();

        ProfileImage picture = new ProfileImage();
        picture.setLocation("data/test.png");

        user.setProfileImage(picture);
        assertThat(user.getProfileImage(), is(picture));
        assertThat(picture.getUser(), is(user));
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15})
    void UserCanFollowUser(int amount){
        List<User> followers = new ArrayList<>();
        User start = new User();
        for(int i =0; i< amount; i++) {

            User follower = new User();
            start.addFollower(follower);

            assertThat(start.getFollowers(), hasItem(follower));
            assertThat(follower.getFollowing(), hasItem(start));

            assertThat(start.getFollowing(), IsCollectionWithSize.<User>hasSize(0));
            assertThat(follower.getFollowers(), IsCollectionWithSize.<User>hasSize(0));

            followers.add(follower);

            assertThat(start.getFollowers(), containsInAnyOrder(followers.toArray()));
        }
    }

    @Test
    void RetryFollowingUser(){
        User start = new User();
        User follower = new User();

        start.addFollower(follower);
        start.addFollower(follower);

        assertThat(start.getFollowers(), hasItem(follower));
        assertThat(follower.getFollowing(), hasItem(start));

        assertThat(start.getFollowers(), IsCollectionWithSize.<User>hasSize(1));
        assertThat(follower.getFollowing(), IsCollectionWithSize.<User>hasSize(1));

        assertThat(start.getFollowing(), IsCollectionWithSize.<User>hasSize(0));
        assertThat(follower.getFollowers(), IsCollectionWithSize.<User>hasSize(0));

    }

    @Test
    void UserCanUnFollowUser(){
        User start = new User();
        User follower = new User();

        start.addFollower(follower);
        start.removeFollower(follower);

        assertThat(start.getFollowers(), IsCollectionWithSize.<User>hasSize(0));
        assertThat(start.getFollowing(), IsCollectionWithSize.<User>hasSize(0));

        assertThat(follower.getFollowers(), IsCollectionWithSize.<User>hasSize(0));
        assertThat(follower.getFollowing(), IsCollectionWithSize.<User>hasSize(0));

    }


}
