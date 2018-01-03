package com.mercadopago.plugins.components;

import android.support.annotation.NonNull;

import com.mercadopago.components.RendererFactory;
import com.mercadopago.plugins.PluginComponent;

/**
 * Created by nfortuna on 1/3/18.
 */

public class CustomPayment extends PluginComponent {

    static {
        RendererFactory.register(CustomPayment.class, CustomPaymentRenderer.class);
    }

    public CustomPayment(@NonNull Props props) {
        super(props);
    }


}
