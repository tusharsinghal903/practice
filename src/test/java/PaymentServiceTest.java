import org.example.PaymentGateway;
import org.example.PaymentResponse;
import org.example.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPaymentSuccess() {
        String orderId = "123";
        double amount = 123D;
        when(paymentGateway.makePayment(orderId, amount)).thenReturn(Optional.of(constructSuccessPaymentResponse()));
        boolean isPaymentDone = paymentService.processPayment(orderId,amount);
        verify(paymentGateway).makePayment(orderId,amount);
        assertEquals(isPaymentDone, true);
    }

    @Test
    void testPaymentFailure() {
        String orderId = "123";
        double amount = 123D;
        when(paymentGateway.makePayment(orderId, amount)).thenReturn(Optional.of(constructFailurePaymentResponse()));
        boolean isPaymentDone = paymentService.processPayment(orderId,amount);
        verify(paymentGateway).makePayment(orderId,amount);
        assertEquals(isPaymentDone, false);
    }

    @Test
    void testUnknownPaymentResponse() {
        String orderId = "123";
        double amount = 123D;
        when(paymentGateway.makePayment(orderId, amount)).thenReturn(Optional.empty());
        boolean isPaymentDone = paymentService.processPayment(orderId,amount);
        verify(paymentGateway).makePayment(orderId,amount);
        assertEquals(isPaymentDone, false);
    }

    private PaymentResponse constructSuccessPaymentResponse() {
        return new PaymentResponse(true, "Success");
    }

    private PaymentResponse constructFailurePaymentResponse() {
        return new PaymentResponse(false, "Failed");
    }
}
