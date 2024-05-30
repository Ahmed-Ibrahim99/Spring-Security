package com.amaghrabi.security.gateway;

import com.amaghrabi.security.model.PaymentRequest;
import com.amaghrabi.security.model.PaymentResponse;
import com.amaghrabi.security.service.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class GenericPaymentGateway implements PaymentGateway {

    private final VisaPaymentGateway visaPaymentGateway;

    private final MastercardPaymentGateway mastercardPaymentGateway;

    public GenericPaymentGateway(VisaPaymentGateway visaPaymentGateway,
                                 MastercardPaymentGateway mastercardPaymentGateway) {
        this.visaPaymentGateway = visaPaymentGateway;
        this.mastercardPaymentGateway = mastercardPaymentGateway;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        String cardNumber = paymentRequest.getCardNumber();

        if (visaPaymentGateway.supports(cardNumber)) {
            return visaPaymentGateway.processPayment(paymentRequest);
        } else if (mastercardPaymentGateway.supports(cardNumber)) {
            return mastercardPaymentGateway.processPayment(paymentRequest);
        } else {
            return new PaymentResponse(false, "Unsupported card provider");
        }
    }
}
