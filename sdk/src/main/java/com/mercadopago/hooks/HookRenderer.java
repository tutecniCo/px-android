package com.mercadopago.hooks;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercadopago.components.Renderer;

public abstract class HookRenderer extends Renderer<HookComponent> {

    @Override
    @CallSuper
    public View render() {

        final TextView view = new TextView(context);
        view.setText();
        view.setTextSize(60);

        return view;
    }

    public abstract View createView(@NonNull final ViewGroup parent);
}
