package com.mercadopago.hooks.components;

import android.view.LayoutInflater;
import android.view.View;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;

/**
 * Created by nfortuna on 10/24/17.
 */

public class PaymentMethodConfirmRenderer extends Renderer<PaymentMethodConfirm> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context).inflate(R.layout.mpsdk_example_component_hook, null);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                component.onContinue();
            }
        });

        return view;
    }
}
