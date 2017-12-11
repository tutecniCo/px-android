package com.mercadopago.plugins.model;

import android.support.annotation.DrawableRes;

/**
 * Created by nfortuna on 12/11/17.
 */

public class PaymentMethodInfo {

    public final String name;
    public final @DrawableRes int icon;

    public PaymentMethodInfo(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }
}
