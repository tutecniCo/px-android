package com.mercadopago.hooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;
import com.mercadopago.hooks.components.*;

public class PaymentMethodConfirmRenderer extends Renderer<com.mercadopago.hooks.components.PaymentMethodConfirm> {

    @Override
    public View render(final Context context, final com.mercadopago.hooks.components.PaymentMethodConfirm component) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_example_component_hook, null);

        final TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(component.getProps().paymentData.getPaymentMethod().getName());

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                component.onContinue();
            }
        });

        return view;
    }
}
