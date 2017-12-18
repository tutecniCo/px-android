package com.mercadopago.plugins;

import com.mercadopago.components.ExternalComponent;

/**
 * Created by nfortuna on 12/11/17.
 */

public abstract class PaymentPlugin {

//    protected Context context;
//
//    public PaymentPlugin(Context context) {
//        this.context = context;
//    }

    public abstract ExternalComponent createPaymentComponent();
}
