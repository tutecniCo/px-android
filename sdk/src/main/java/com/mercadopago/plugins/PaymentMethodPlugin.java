package com.mercadopago.plugins;

import com.mercadopago.components.Component;
import com.mercadopago.plugins.model.PaymentMethodInfo;

/**
 * Created by nfortuna on 12/11/17.
 */

public abstract class PaymentMethodPlugin {

    public static final String POSIION_UP = "position_up";
    public static final String POSIION_DOWN = "position_down";

    public String getPosition() {
        return POSIION_DOWN;
    }

    public abstract PaymentMethodInfo getPaymentMethodInfo();
    public abstract Component createConfigurationComponent();

}
