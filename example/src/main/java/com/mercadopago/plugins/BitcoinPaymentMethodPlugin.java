package com.mercadopago.plugins;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mercadopago.examples.R;
import com.mercadopago.plugins.model.PaymentMethodInfo;

/**
 * Created by nfortuna on 12/13/17.
 */

public class BitcoinPaymentMethodPlugin extends PaymentMethodPlugin {

    public BitcoinPaymentMethodPlugin(Context context) {
        super(context);
    }

    @Override
    public PaymentMethodInfo getPaymentMethodInfo() {
        return new PaymentMethodInfo(
            "bitcoin",
            context.getString(R.string.bitcoin_name),
            R.drawable.mpsdk_ic_bitcoin
        );
    }

    @Override
    public PluginComponent createConfigurationComponent(@NonNull PluginComponent.Props props) {
        return null;
    }
}