package com.mercadopago.plugins;

import android.support.annotation.NonNull;

import com.mercadopago.core.CheckoutStore;
import com.mercadopago.plugins.components.BitcoinPayment;
import com.mercadopago.plugins.components.NicoPayment;

/**
 * Created by nfortuna on 12/13/17.
 */

public class BitcoinPaymentPlugin extends PaymentPlugin {

    @Override
    public PluginComponent createPaymentComponent(@NonNull PluginComponent.Props props) {
        return new BitcoinPayment(props.toBuilder().setToolbarVisible(false).build());
    }
}