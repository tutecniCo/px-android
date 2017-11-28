package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.hooks.components.PaymentMethodConfirm;
import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public class ExampleHooks extends DefaultCheckoutHooks {

    @Override
    public Hook onPaymentMethodSelected(@NonNull final PaymentData paymentData,
                                        @NonNull final DecorationPreference decorationPreference) {

        final HookComponent.Props props = new HookComponent.Props(
                HooksStore.getInstance(),
                paymentData,
                decorationPreference,
                "Example hooks",
                true);

        final PaymentMethodConfirm component = new PaymentMethodConfirm(props);
        final Hook hook = new Hook(component);
        return hook;
    }

//    @Override
//    public Hook onConfirmPayment(@NonNull PaymentData paymentData) {
//
//
//        return super.onConfirmPayment(paymentData);
//    }
}
