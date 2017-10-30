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

        final HookComponent.Props props = new HookComponent.Props(
                HooksStore.getInstance(),
                paymentData);

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
