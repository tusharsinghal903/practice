package org.example;

import java.util.Optional;

public class PaymentService {

    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public boolean processPayment(String orderId, double amount) {
        Optional<PaymentResponse> response = paymentGateway.makePayment(orderId, amount);
        return response.map(PaymentResponse::isSuccess).orElse(false);
    }
}
