package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mercadopago.paymentresult.CongratsHeaderComponent;
import com.mercadopago.paymentresult.CongratsHeaderRenderer;
import com.mercadopago.paymentresult.SubtitleComponent;
import com.mercadopago.paymentresult.SubtitleRenderer;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vaserber on 10/17/17.
 */

public class ComponentManager implements ActionDispatcher {

    private Activity activity;
    private Component root;
    private ActionsListener actionsListener;
    private Map<Class, Class> renderersRegistry = new HashMap<>();

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;

        renderersRegistry.put(CongratsHeaderComponent.class, CongratsHeaderRenderer.class);
        renderersRegistry.put(SubtitleComponent.class, SubtitleRenderer.class);
    }

    public void setComponent(@NonNull final Component component) {

        root = component;
        Renderer renderer = null;

        try {
            renderer = (Renderer) renderersRegistry.get(root.getClass()).newInstance();
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }

        if (renderer != null) {
            activity.setContentView(renderer.render());
        }
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
