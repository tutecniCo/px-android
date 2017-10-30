package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.Component;
import com.mercadopago.model.PaymentData;

public abstract class HookComponent extends Component<HookComponent.Props> {

    public HookComponent(@NonNull HookComponent.Props props) {
        super(props);
    }

    public static class Props {

        public final HooksStore store;
        public final PaymentData paymentData;

        public Props(HooksStore store, PaymentData paymentData) {
            this.store = store;
            this.paymentData = paymentData;
        }
    }
}
