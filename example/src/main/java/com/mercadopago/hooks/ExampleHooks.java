package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.hooks.components.PaymentMethodConfirm;
import com.mercadopago.model.PaymentResult;

/**
 * Created by nfortuna on 10/24/17.
 */

public class ExampleHooks implements CheckoutHooks {

    @Override
    public Hook onPaymentMethodSelected(@NonNull final PaymentResult result, @NonNull final ActionDispatcher dispatcher) {

        final PaymentMethodConfirm component = new PaymentMethodConfirm(new PaymentMethodConfirm.Props(result), dispatcher);
        final Hook hook = new Hook(component);

        return hook;
    }
}
