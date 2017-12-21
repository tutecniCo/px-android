package com.mercadopago.plugins.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mercadopago.components.Renderer;
import com.mercadopago.examples.R;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPaymentMethodRenderer extends Renderer<NicoPaymentMethod> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context).inflate(R.layout.mpsdk_pmplugin_nicopay_config, null);

        view.findViewById(R.id.button_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.next();
            }
        });

        final EditText docu = view.findViewById(R.id.docu);

        docu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                component.setDocument(s.toString());
            }
        });

        return view;
    }
}