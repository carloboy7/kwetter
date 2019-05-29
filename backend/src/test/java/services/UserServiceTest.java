package services;

import dbal.context.SimpleMemoryContext;
import dbal.context.UserMemoryContext;
import dbal.repositories.SimpleRepository;
import dbal.repositories.UserRepository;
import entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends BaseServiceTest<UserService> {
    @BeforeEach
    void setUp() {
        simpleService = new UserService(new UserRepository(new UserMemoryContext()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInvalidUser(String method) throws NoSuchMethodException {
        testBaseService(new User(), method, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testValidUserOnlyWithName(String method) throws NoSuchMethodException {
        User user = new User();
        user.setName("name");
        testBaseService(user, method, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInValidUserWithBio(String method) throws NoSuchMethodException {
        User user = new User();
        user.setName("name");
        /*161 characters*/
        user.setBio("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and_");
        testBaseService(user, method, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testValidUserWithBio(String method) throws NoSuchMethodException {
        User user = new User();
        user.setName("name");
        /*160 characters*/
        user.setBio("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and");
        testBaseService(user, method, false);
    }

    @Test
    void updateUserException(){
        User user = new User();
        assertThrows(EntityNotFoundException.class, () -> simpleService.update(user));
    }

}
