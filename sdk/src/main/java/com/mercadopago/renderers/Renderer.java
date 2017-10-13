package com.mercadopago.renderers;

import android.content.Context;
import android.view.View;

/**
 * Created by vaserber on 10/13/17.
 */

public abstract class Renderer<T> {
    public Renderer(Context context) {
        bindViews(context);
    }

    public abstract View render(T component);

    protected abstract void bindViews(Context context);
}

