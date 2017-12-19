package com.mercadopago.paymentresult.components;

import android.view.LayoutInflater;
import android.view.View;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;


/**
 * Created by mromar on 11/22/17.
 */

public class TestCustomRenderer extends Renderer<TestCustomComponent> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context).inflate(R.layout.mpsdk_test_custom_render, null);
        return view;
    }
}

