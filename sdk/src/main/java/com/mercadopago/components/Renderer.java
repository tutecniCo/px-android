package com.mercadopago.components;

import android.content.Context;
import android.view.View;

/**
 * Created by vaserber on 10/13/17.
 */

public abstract class Renderer<T extends Component> {

    private T component;
    private Context context;

    public void setComponent(final T component) {
        this.component = component;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    public abstract View render(final Context context, final T component);
}