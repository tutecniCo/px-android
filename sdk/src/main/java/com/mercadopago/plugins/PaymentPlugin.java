package com.mercadopago.plugins;

import com.mercadopago.components.PluginComponent;

/**
 * Created by nfortuna on 12/11/17.
 */

public abstract class PaymentPlugin {
    public abstract PluginComponent createPaymentComponent();
}
