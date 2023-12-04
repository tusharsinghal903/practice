import org.example.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TaskManagerTest {

    private TaskManager spyTaskManager;

    @BeforeEach
    void setUp() {
        spyTaskManager = spy(new TaskManager());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteTaskA() {
        // Mocking executeTaskB behavior
        when(spyTaskManager.executeTaskB()).thenReturn("MockedTaskB Result");

        // Perform the test on executeTaskA
        String result = spyTaskManager.executeTaskA();

        // Verify interactions and assertions
        assertEquals("MockedTaskB Result", result);
    }
}
