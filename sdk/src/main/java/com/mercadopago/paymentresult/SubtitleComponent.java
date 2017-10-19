package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class SubtitleComponent extends Component<String> {
    private String subtitle;

    public SubtitleComponent(String subtitle, final ActionDispatcher dispatcher) {
        super(subtitle, dispatcher);
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void applyProps(@NonNull String subtitle) {
        this.subtitle = subtitle;
    }
}

