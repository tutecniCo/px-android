package com.mercadopago.hooks.components;

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

    @Override
    public void applyProps(@NonNull final Props props) {

    }

    public void onContinue() {
        getDispatcher().dispatch(Action.continueAction());
    }
}
