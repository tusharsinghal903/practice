import org.example.User;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalUserCount() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User()));
        int totalUsers = userService.getTotalUserCount();
        assertEquals(totalUsers, 1);
        verify(userRepository).getAllUsers();
    }

}
