package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;

public class DefaultCheckoutHooks implements CheckoutHooks {
    @Override
    public Hook onPaymentMethodSelected(@NonNull PaymentData paymentData) {
        return null;
    }

    @Override
    public Hook onConfirmPayment(@NonNull PaymentData paymentData) {
        return null;
    }
}