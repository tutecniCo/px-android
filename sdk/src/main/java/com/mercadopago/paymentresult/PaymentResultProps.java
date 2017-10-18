package com.mercadopago.paymentresult;

import com.mercadopago.components.Props;

/**
 * Created by vaserber on 10/18/17.
 */

public class PaymentResultProps extends Props {

    public final String title;
    public final String subtitle;

    public PaymentResultProps(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
