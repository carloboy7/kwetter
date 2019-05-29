package services;

import entities.user.User;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeAll;

import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import java.lang.reflect.Method;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class BaseServiceTest<T extends SimpleService> {
    private static final Logger LOG = Logger.getLogger(BaseServiceTest.class.getName());
    private static ExecutableValidator  executableValidator;
    protected T simpleService;

    @BeforeAll
    static void init() {
        executableValidator = Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .forExecutables();
    }

    protected void testBaseService(Object obj, String methodName, boolean errorExpected) throws NoSuchMethodException {
        Method create = null;

        Method[] methodArray = simpleService.getClass().getMethods();
        for(int i=0; i < methodArray.length; i++) {
            if(methodArray[i].getName().equalsIgnoreCase(methodName)) {
                create = methodArray[i];
                break;
            }
        }

        if(errorExpected) {
            Set<ConstraintViolation<SimpleService>> errors = executableValidator.validateParameters(simpleService, create, new Object[]{obj});
            assertThat(errors, not(IsCollectionWithSize.<ConstraintViolation>hasSize(0)));
            errors.forEach(x -> LOG.info(x.getMessage()));
        } else {
            assertThat(executableValidator.validateParameters(simpleService, create, new Object[]{obj}), IsCollectionWithSize.<ConstraintViolation>hasSize(0));
        }

    }

}
