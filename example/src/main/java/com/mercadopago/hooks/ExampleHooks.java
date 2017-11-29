package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.hooks.components.PaymentMethodConfirm;
import com.mercadopago.hooks.components.PaymentTypeConfirm;
import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public class ExampleHooks extends DefaultCheckoutHooks {

    @Override
    public Hook afterPaymentTypeSelected(@NonNull final String typeId,
                                         @NonNull final DecorationPreference decorationPreference) {

        final HookComponent.Props props = new HookComponent.Props(
                HooksStore.getInstance(),
                typeId,
                null,
                decorationPreference,
                "Hook 1",
                true);

        final PaymentTypeConfirm component = new PaymentTypeConfirm(props);
        final Hook hook = new Hook(component);
        return hook;
    }

    @Override
    public Hook afterPaymentMethodSelected(@NonNull final PaymentData paymentData,
                                           @NonNull final DecorationPreference decorationPreference) {

        final HookComponent.Props props = new HookComponent.Props(
                HooksStore.getInstance(),
                null,
                paymentData,
                decorationPreference,
                "Hook 2",
                true);

        final PaymentMethodConfirm component = new PaymentMethodConfirm(props);
        final Hook hook = new Hook(component);
        return hook;
    }
}
