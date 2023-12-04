import org.example.MathOperations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathOperationsTest {

    private static MathOperations mathOperations;

    @BeforeAll
    static void setUp() {
        mathOperations = new MathOperations();
    }

    @Test
    public void testDivide() {
        int a =5;
        int b = 6;
        int res = mathOperations.divide(5,6);
        assertEquals(res, 0);
    }

    @Test
    public void testDivideByZero() {
        ArithmeticException arithmeticException = Assertions.assertThrows(ArithmeticException.class, () -> {
            mathOperations.divide(5,0);
        });
        assertEquals("Cannot divide by zero", arithmeticException.getMessage());
    }

}
