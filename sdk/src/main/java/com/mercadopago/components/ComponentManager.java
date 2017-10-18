package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by vaserber on 10/17/17.
 */

public class ComponentManager implements ActionDispatcher {

    private Activity activity;
    private Renderer root;
    private ActionsListener actionsListener;

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public void setComponent(@NonNull final Renderer component) {
        root = component;
        activity.setContentView(root.render());
    }

    public void setActionsListener(final ActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void dispatch(final Action action) {
        if (actionsListener != null) {
            actionsListener.onAction(action);
        }
    }
}
