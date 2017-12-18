package com.mercadopago.paymentresult.components;

import com.mercadopago.components.RendererFactory;

/**
 * Created by mromar on 11/22/17.
 */

public class TestCustomRenderer extends CustomComponent {

    static {
        RendererFactory.register(TestCustomRenderer.class, TestCustomRenderer.class);
    }

    public void next() {
        //getDispatcher().dispatch(new NextAction());
    }
}
