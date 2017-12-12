package com.mercadopago.paymentresult.components;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.paymentresult.PaymentMethodProvider;
import com.mercadopago.paymentresult.props.PaymentMethodProps;
import com.mercadopago.paymentresult.props.TotalAmountProps;

/**
 * Created by mromar on 11/22/17.
 */

public class CustomComponent extends Component<PaymentMethodProps> {

    private PaymentMethodProvider provider;

    public CustomComponent(@NonNull final PaymentMethodProps props,
                           @NonNull final ActionDispatcher dispatcher,
                           @NonNull final PaymentMethodProvider provider) {
        super(props, dispatcher);

        this.provider = provider;
    }

    public Drawable getImage() {
        return provider.getImage(props.paymentMethod);
    }

}
