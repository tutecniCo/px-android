package com.mercadopago.plugins;

import com.mercadopago.components.PluginComponent;
import com.mercadopago.plugins.components.NicoPayment;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentPlugin extends PaymentPlugin {

    @Override
    public PluginComponent createPaymentComponent() {
        return new NicoPayment();
    }
}