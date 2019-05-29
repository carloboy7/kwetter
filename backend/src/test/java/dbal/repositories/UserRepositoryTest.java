package dbal.repositories;

import dbal.context.BaseContext;
import dbal.context.UserContext;
import entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    UserContext userContext;


    @InjectMocks
    private UserRepository userRepository;


    @Test
    void basicCreateTest(){
        User user = new User();
        user.setName("name");
        userRepository.create(user);

        verify(userContext).create(ArgumentMatchers.eq(user));
        verify(userContext, times(1)).create(user);
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void basicUpdateTest(){
        User user = new User();
        user.setName("name");
        userRepository.update(user);
        verify(userContext).update(ArgumentMatchers.eq(user));
        verify(userContext, times(1)).update(user);
        verifyNoMoreInteractions(userContext);
    }
    @Test
    void basicUpdateExceptionTest(){
        User user = new User();
        user.setName("name");

        doThrow(new EntityNotFoundException()).when(userContext).update(user);

        assertThrows(EntityNotFoundException.class, () -> userRepository.update(user));
        verify(userContext, times(1)).update(user);
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void basicDeleteTest(){
        User user = new User();
        user.setName("name");
        userRepository.delete(user);

        verify(userContext).delete(ArgumentMatchers.eq(user));
        verify(userContext, times(1)).delete(user);
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void basicFindById(){
        userRepository.findById(1);

        verify(userContext).findById(ArgumentMatchers.eq(1));
        verify(userContext, times(1)).findById(1);
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void basicFindAll(){
        userRepository.findAll();

        verify(userContext, times(1)).findAll();
        verifyNoMoreInteractions(userContext);
    }

    @Test
    void basicFindBy(){
        userRepository.findBy("field", "value");

        verify(userContext, times(1)).findBy("field", "value");
        verifyNoMoreInteractions(userContext);
    }


}
