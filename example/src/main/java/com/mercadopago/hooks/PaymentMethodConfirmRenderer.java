package com.mercadopago.hooks;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;

public class PaymentMethodConfirmRenderer extends Renderer<PaymentMethodConfirm> {

    @Override
    public View render() {

        final View view = LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_example_component_hook, null);

        final TextView label = (TextView) view.findViewById(R.id.label);

        label.setText(component.props.paymentData.getPaymentMethod().getName());

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                component.onContinue();
            }
        });

        return view;
    }
}
