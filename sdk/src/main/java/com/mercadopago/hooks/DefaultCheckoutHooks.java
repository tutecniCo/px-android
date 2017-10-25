package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;

/**
 * Created by nfortuna on 10/25/17.
 */

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