package services;

import dbal.context.KweetMemoryContext;
import dbal.context.SimpleMemoryContext;
import dbal.repositories.KweetRepository;
import dbal.repositories.SimpleRepository;
import entities.kweet.Kweet;
import entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class KweetServiceTest extends BaseServiceTest<KweetService> {
    @BeforeEach
    void setUp() {
        simpleService = new KweetService(new KweetRepository(new KweetMemoryContext()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInvalidKweet(String method) throws NoSuchMethodException {
        testBaseService(new Kweet(), method, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testValidKweet(String method) throws NoSuchMethodException {

        User user = new User();
        user.setName("name");

        Kweet kweet = new Kweet();
        kweet.setUser(user);
        kweet.setText("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and");
        testBaseService(kweet, method, false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInValidKweetNoText(String method) throws NoSuchMethodException {

        User user = new User();
        user.setName("name");

        Kweet kweet = new Kweet();
        kweet.setUser(user);
        testBaseService(kweet, method, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInValidKweetLength(String method) throws NoSuchMethodException {

        User user = new User();
        user.setName("name");

        Kweet kweet = new Kweet();
        kweet.setUser(user);
        /* 161 characters*/
        kweet.setText("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and_");
        testBaseService(kweet, method, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testinValidKweetZeroLength(String method) throws NoSuchMethodException {

        User user = new User();
        user.setName("name");

        Kweet kweet = new Kweet();
        kweet.setUser(user);
        /* 161 characters*/
        kweet.setText("");
        testBaseService(kweet, method, true);
    }


    @ParameterizedTest
    @ValueSource(strings = {"create", "delete", "update"})
    void testInValidKweetUser(String method) throws NoSuchMethodException {

        Kweet kweet = new Kweet();
        kweet.setText("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and");
        testBaseService(kweet, method, true);
    }

    @Test
    void updateKweetException(){
        Kweet kweet = new Kweet();
        assertThrows(EntityNotFoundException.class, () -> simpleService.update(kweet));
    }

}
