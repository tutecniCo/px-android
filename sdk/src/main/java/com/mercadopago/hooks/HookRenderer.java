package com.mercadopago.hooks;

import android.view.View;
import android.widget.TextView;

import com.mercadopago.components.Renderer;

public class HookRenderer extends Renderer<HookComponent> {

    @Override
    public View render() {

        final TextView view = new TextView(context);
        view.setText("Seeeeeeeee fucking HOOK");
        view.setTextSize(60);

        return view;
    }
}
