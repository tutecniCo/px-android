package com.mercadopago.plugins;

import android.content.Context;

import com.mercadopago.components.PluginComponent;
import com.mercadopago.plugins.model.PaymentMethodInfo;

/**
 * Created by nfortuna on 12/11/17.
 */

public abstract class PaymentMethodPlugin {

    public static final String POSIION_TOP = "position_up";
    public static final String POSIION_BOTTOM = "position_down";

    protected Context context;

    public PaymentMethodPlugin(Context context) {
        this.context = context;
    }

    public String displayOrder() {
        return POSIION_TOP;
    }

    public abstract PaymentMethodInfo getPaymentMethodInfo();

    public abstract PluginComponent createConfigurationComponent();

}
