package org.example;

import java.util.Optional;

public interface PaymentGateway {
    Optional<PaymentResponse> makePayment(String orderId, double amount);
}
