package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.hooks.components.PaymentMethodConfirm;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentResult;

/**
 * Created by nfortuna on 10/24/17.
 */

public class ExampleHooks extends DefaultCheckoutHooks {

    @Override
    public Hook onPaymentMethodSelected(@NonNull final PaymentData paymentData) {

        final ActionDispatcher dispatcher = new ActionDispatcher() {

            @Override
            public void dispatch(Action action) {
                //sarasa
            }
        };

        final PaymentMethodConfirm.Props props = new PaymentMethodConfirm.Props(paymentData);
        final PaymentMethodConfirm component = new PaymentMethodConfirm(props, dispatcher);
        final Hook hook = new Hook(component);
        return hook;
    }
}
