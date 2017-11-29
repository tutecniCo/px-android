package com.mercadopago.hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.BackAction;
import com.mercadopago.components.ComponentManager;
import com.mercadopago.components.NextAction;
import com.mercadopago.components.RendererFactory;

public class HookActivity extends AppCompatActivity implements ActionDispatcher {

    private ComponentManager componentManager;

    public static Intent getIntent(final Context context) {
        final Intent intent = new Intent(context, HookActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RendererFactory.register(HookComponent.class, HookRenderer.class);

        componentManager = new ComponentManager(this);

        final Hook hook = HooksStore.getInstance().getHook();

        if (hook == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        hook.component.setDispatcher(this);
        componentManager.render(hook.component);
    }

    @Override
    public void dispatch(final Action action) {
        if (action instanceof NextAction) {
            setResult(RESULT_OK);
            finish();
        } else if (action instanceof BackAction) {
            onBackPressed();
        }
    }
}