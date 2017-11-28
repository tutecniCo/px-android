package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public interface CheckoutHooks {
    Hook onPaymentMethodSelected(@NonNull final PaymentData paymentData,
                                 @NonNull final DecorationPreference decorationPreference);

    Hook onConfirmPayment(@NonNull final PaymentData paymentData);
}