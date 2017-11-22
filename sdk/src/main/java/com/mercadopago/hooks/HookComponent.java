package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.model.PaymentData;

public abstract class HookComponent extends Component<HookComponent.Props> {

    public HookComponent(@NonNull final Props props) {
        super(props);
    }

    public static class Props {

        public final HooksStore store;
        public final PaymentData paymentData;

        public Props(@NonNull final HooksStore store,
                     @NonNull final PaymentData paymentData) {
            this.store = store;
            this.paymentData = paymentData;
        }
    }
}