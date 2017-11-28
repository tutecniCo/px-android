package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public class DefaultCheckoutHooks implements CheckoutHooks {
    @Override
    public Hook onPaymentMethodSelected(@NonNull PaymentData paymentData,
                                        @NonNull final DecorationPreference decorationPreference) {
        return null;
    }

    @Override
    public Hook onConfirmPayment(@NonNull PaymentData paymentData) {
        return null;
    }
}