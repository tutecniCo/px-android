package com.mercadopago.hooks.components;

import android.support.annotation.NonNull;

import com.mercadopago.components.Action;
import com.mercadopago.components.ContinueAction;
import com.mercadopago.hooks.HookComponent;

public class PaymentMethodConfirm extends HookComponent {

    public PaymentMethodConfirm(@NonNull final Props props) {
        super(props);
    }

//    public void onContinue() {
//        getDispatcher().dispatch(new ContinueAction());
//    }
}
