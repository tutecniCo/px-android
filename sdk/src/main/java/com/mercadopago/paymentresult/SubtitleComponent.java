package com.mercadopago.paymentresult;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class SubtitleComponent extends Component {
    private String subtitle;

    public SubtitleComponent(String subtitle, final ActionDispatcher dispatcher) {
        super(dispatcher);
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }
}

