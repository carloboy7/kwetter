package entities;

import entities.user.User;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class KweetEntityTest {
    @Test
    void MakeSimpleKweet(){
        entities.kweet.Kweet kweet = new entities.kweet.Kweet();
        assertThat(kweet, not(nullValue()));
    }

    @Test
    void SetTextOfKweet(){
        entities.kweet.Kweet kweet = new entities.kweet.Kweet();

        kweet.setText("The content of the kweet");

        assertThat(kweet.getText(), is("The content of the kweet"));
    }

    @Test
    void AddKweetToUser(){
        entities.kweet.Kweet kweet = new entities.kweet.Kweet();
        User user = new User();

        kweet.setUser(user);

        assertThat(kweet.getUser(), sameInstance(user));
        assertThat(user.getKweets(), hasItem(kweet));
    }
}
