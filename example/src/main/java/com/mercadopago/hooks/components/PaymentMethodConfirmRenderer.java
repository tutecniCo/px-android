package com.mercadopago.hooks.components;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.examples.R;
import com.mercadopago.hooks.HookRenderer;

public class PaymentMethodConfirmRenderer extends HookRenderer {

    @Override
    public View renderContents() {

        final View view = LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_example_hook_payment_method_confirm, null);

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
