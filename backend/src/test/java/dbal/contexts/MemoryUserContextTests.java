package dbal.contexts;

import dbal.context.UserMemoryContext;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemoryUserContextTests extends BaseUserContextTest {
    @BeforeEach
    void setUp() {
        baseContext = new UserMemoryContext();
    }
}
