package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.hooks.HookComponent;
import com.mercadopago.model.PaymentData;

public class PaymentMethodConfirm extends HookComponent {

    public PaymentMethodConfirm(@NonNull final Props props) {
        super(props);
    }

    public void onContinue() {
        getDispatcher().dispatch(Action.continueAction());
    }
}
