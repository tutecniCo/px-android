package com.mercadopago.plugins;

import android.content.Context;

import com.mercadopago.components.PluginComponent;
import com.mercadopago.examples.R;
import com.mercadopago.plugins.components.NicoPaymentMethod;
import com.mercadopago.plugins.model.PaymentMethodInfo;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentMethodPlugin extends PaymentMethodPlugin {

    public NicoPaymentMethodPlugin(Context context) {
        super(context);
    }

    @Override
    public PaymentMethodInfo getPaymentMethodInfo() {
        return new PaymentMethodInfo(
            "nicopay",
            context.getString(R.string.nicopay_name),
            R.drawable.mpsdk_ic_nicopay,
            context.getString(R.string.nicopay_description)
        );
    }

    @Override
    public String displayOrder() {
        return PaymentMethodPlugin.POSIION_BOTTOM;
    }

    @Override
    public PluginComponent createConfigurationComponent() {
        return new NicoPaymentMethod();
    }
}