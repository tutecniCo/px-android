package com.mercadopago.plugins;

import com.mercadopago.components.ExternalComponent;
import com.mercadopago.plugins.components.NicoPayment;
import com.mercadopago.plugins.components.NicoPaymentMethod;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentPlugin extends PaymentPlugin {

    @Override
    public ExternalComponent createPaymentComponent() {
        return new NicoPayment();
    }
}