package com.mercadopago.plugins;

import android.support.annotation.NonNull;

import com.mercadopago.core.CheckoutStore;
import com.mercadopago.plugins.components.NicoPayment;
import com.mercadopago.plugins.components.NicoPaymentMethod;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentPlugin extends PaymentPlugin {

    @Override
    public PluginComponent createPaymentComponent(@NonNull final PluginComponent.Props props) {
        return new NicoPayment(
                props.toBuilder()
                    .setToolbarVisible(false)
                    .build()
        );
    }
}