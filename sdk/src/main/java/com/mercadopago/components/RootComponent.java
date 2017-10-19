package com.mercadopago.components;

import android.support.annotation.NonNull;

import com.mercadopago.paymentresult.PaymentResultProps;
import com.mercadopago.paymentresult.SubtitleComponent;

/**
 * Created by nfortunato on 10/19/17.
 */

public class RootComponent extends Component<Void> {

    private Component contentComponent;

    public RootComponent(@NonNull final ActionDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void setProps(@NonNull final Void props) {
        //Root component do not have have props
    }
}
