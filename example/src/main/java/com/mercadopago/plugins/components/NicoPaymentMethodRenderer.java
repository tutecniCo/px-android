package com.mercadopago.plugins.components;

import android.view.LayoutInflater;
import android.view.View;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentMethodRenderer extends Renderer<NicoPaymentMethodComponent> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context).inflate(R.layout.mpsdk_pmplugin_nicopay_config, null);

        component.next();

        return view;
    }
}