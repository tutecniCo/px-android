package com.mercadopago.hooks;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;

public abstract class HookRenderer extends Renderer<HookComponent> {

    @Override
    @CallSuper
    public View render() {

        final View view = LayoutInflater.from(context).inflate(R.layout.mpsdk_hook, null);

        view.fin

        return view;
    }

    public abstract View renderContents(@NonNull final ViewGroup parent);
}
