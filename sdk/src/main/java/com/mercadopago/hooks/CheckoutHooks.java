package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;

public interface CheckoutHooks {
    Hook onPaymentMethodSelected(@NonNull final PaymentData paymentData);

    Hook onConfirmPayment(@NonNull final PaymentData paymentData);
}