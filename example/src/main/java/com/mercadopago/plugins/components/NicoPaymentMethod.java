package com.mercadopago.plugins.components;

import com.mercadopago.components.PluginComponent;
import com.mercadopago.components.NextAction;
import com.mercadopago.components.RendererFactory;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentMethod extends PluginComponent {

    static {
        RendererFactory.register(NicoPaymentMethod.class, NicoPaymentMethodRenderer.class);
    }

    public void next() {
        getDispatcher().dispatch(new NextAction());
    }
}