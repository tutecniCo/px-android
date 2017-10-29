package com.mercadopago.hooks.components;

import android.support.annotation.NonNull;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.model.PaymentData;

/**
 * Created by nfortuna on 10/24/17.
 */

public class PaymentMethodConfirm extends Component<PaymentMethodConfirm.Props> {

    public PaymentMethodConfirm(@NonNull final Props props,
                                @NonNull final ActionDispatcher dispatcher) {
        super(props, dispatcher);
    }

    @Override
    public void applyProps(@NonNull final PaymentMethodConfirm.Props props) {

    }

    public void onContinue() {
        getDispatcher().dispatch(Action.continueAction());
    }

    public static class Props {
        private PaymentData paymentData;

        public Props(@NonNull final PaymentData paymentData) {
            this.paymentData = paymentData;
        }
    }
}
