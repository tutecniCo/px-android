package com.mercadopago.hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.components.ComponentManager;
import com.mercadopago.components.RendererFactory;

/**
 * Created by nfortuna on 10/24/17.
 */

public class HookActivity extends AppCompatActivity {

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

//        final Hook hook = HooksStore.getInstance().getHook();
//
//        if (hook == null) {
//            setResult(RESULT_CANCELED);
//            finish();
//            return;
//        }

        final HookComponent hook = new HookComponent(
                new HookComponent.Props(HooksStore.getInstance(),
                "Sarasa!!!"), componentManager);

        componentManager.render(hook);
    }
}