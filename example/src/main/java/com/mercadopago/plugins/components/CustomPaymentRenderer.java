package com.mercadopago.plugins.components;

import android.view.View;
import android.widget.TextView;

import com.mercadopago.plugins.PluginRenderer;

/**
 * Created by nfortuna on 1/3/18.
 */

public class CustomPaymentRenderer extends PluginRenderer {


    @Override
    public View renderContents() {

        TextView view = new TextView(context);
        view.setText("Custom payment plugin... do nothing");

        return view;
    }
}
