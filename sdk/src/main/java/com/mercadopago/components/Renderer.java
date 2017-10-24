package com.mercadopago.components;

import android.content.Context;
import android.view.View;

/**
 * Created by vaserber on 10/13/17.
 */

public abstract class Renderer<T extends Component> {

    protected T component;
    protected Context context;

    public void setComponent(T component) {
        this.component = component;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract View render();
}

