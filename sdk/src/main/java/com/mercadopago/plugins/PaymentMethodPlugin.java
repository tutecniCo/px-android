package com.mercadopago.plugins;

import com.mercadopago.components.Component;
import com.mercadopago.plugins.model.PaymentMethodInfo;

/**
 * Created by nfortuna on 12/11/17.
 */

public interface PaymentMethodPlugin {

    PaymentMethodInfo getPaymentMethodInfo();

    Component createConfigurationComponent();
}
