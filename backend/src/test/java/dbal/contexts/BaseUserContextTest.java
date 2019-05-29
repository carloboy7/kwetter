package dbal.contexts;

import dbal.context.BaseContext;
import entities.user.User;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class BaseUserContextTest {
    protected BaseContext<User> baseContext;


    @Test
    void constructContext() {
        assertThat(baseContext.findAll(), IsCollectionWithSize.<User>hasSize(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    void addNewUsers(int amount) {

        for (int i = 0; i < amount; i++) {
            User user = new User();
            baseContext.create(user);

            assertThat(user.getId(), not(0));
            assertThat(baseContext.findAll(), IsCollectionWithSize.<User>hasSize(i + 1));
        }
    }

    @Test
    void findUserById() {


        User user = new User();
        baseContext.create(user);
        assertThat(baseContext.findById(user.getId()), is(user));
    }

    @Test
    void findUserByName() {


        User user = new User();
        user.setName("Henk");

        baseContext.create(user);

        assertThat(baseContext.findBy("name", "Henk"), hasItem(user));
    }

    @Test
    void findUserByWebsite() {


        User user = new User();
        user.setWebsite("http://test.org");

        baseContext.create(user);

        assertThat(baseContext.findBy("website", "http://test.org"), hasItem(user));
    }

    @Test
    void findUserByStringId() {

        User user = new User();

        baseContext.create(user);

        assertThat(baseContext.findBy("id", user.getId()), hasItem(user));
    }

    @Test
    void deleteUser() {


        User user = new User();

        baseContext.create(user);
        baseContext.delete(user);
        assertThat(baseContext.findAll(), IsCollectionWithSize.<User>hasSize(0));
    }

    @Test
    void updateUserException() {


        User user = new User();

        assertThrows(EntityNotFoundException.class, () -> baseContext.update(user));
        assertThat(baseContext.findAll(), IsCollectionWithSize.<User>hasSize(0));
    }

    @Test
    void updateUser() {


        User user = new User();

        user.setName("hoi");
        baseContext.create(user);

        assertThat(baseContext.findBy("name", "hoi"), IsCollectionWithSize.<User>hasSize(1));

        user.setName("Test");

        baseContext.update(user);
        assertThat(baseContext.findBy("name", "Test"), IsCollectionWithSize.<User>hasSize(1));
    }
}
