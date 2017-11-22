package com.mercadopago.hooks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.components.Renderer;

public class HookRenderer extends Renderer<HookComponent> {

    @Override
    public View render(final Context context, final HookComponent component) {

        final TextView view = new TextView(context);
        view.setText("Seeeeeeeee fucking HOOK");
        view.setTextSize(60);

        return view;
    }
}
