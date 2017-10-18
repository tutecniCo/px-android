package com.mercadopago.paymentresult;

import com.mercadopago.components.Action;

/**
 * Created by vaserber on 10/18/17.
 */

public class LogAction extends Action {

    public final String text;

    public LogAction(final String text) {
        this.text = text;
    }
}
