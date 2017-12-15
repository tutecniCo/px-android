package com.mercadopago.plugins.components;

import com.mercadopago.components.ExternalComponent;
import com.mercadopago.components.NextAction;
import com.mercadopago.components.RendererFactory;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentMethodComponent extends ExternalComponent {

    static {
        RendererFactory.register(NicoPaymentMethodComponent.class, NicoPaymentMethodRenderer.class);
    }

    public void next() {
        getDispatcher().dispatch(new NextAction());
    }
}