package com.mercadopago.plugins.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;

/**
 * Created by nfortuna on 12/13/17.
 */

public class BitcoinPaymentRenderer extends Renderer<BitcoinPayment> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_pmplugin_bitcoin_payment, null);
        view.findViewById(R.id.logo).startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate) );
        return view;
    }
}