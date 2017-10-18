package com.mercadopago.components;

import android.content.Context;
import android.view.View;

import com.mercadopago.paymentresult.SubtitleComponent;

/**
 * Created by vaserber on 10/13/17.
 */

public abstract class Renderer<T extends Component> {

    protected T component;

    public Renderer(T component, Context context) {
        this.component = component;
        bindViews(context);
    }

    public abstract View render();

    protected abstract void bindViews(Context context);
}

