package com.mercadopago.hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.components.ComponentManager;
import com.mercadopago.components.Mutator;
import com.mercadopago.components.MutatorPropsListener;

/**
 * Created by nfortuna on 10/24/17.
 */

public class HookActivity extends AppCompatActivity implements MutatorPropsListener {

    private ComponentManager componentManager;

    public static Intent getIntent(final Context context) {
        final Intent intent = new Intent(context, HookActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.mpsdk_activity_hook);

        final Mutator mutator = new Mutator() {

            final

            @Override
            public void setPropsListener(@NonNull final MutatorPropsListener listener) {

            }
        };

        mutator.setPropsListener();

        componentManager = new ComponentManager(this);

        final Hook hook = HooksStore.getInstance().getHook();

        if (hook == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

//        componentManager.setActionsListener(presenter);
        componentManager.setComponent(hook.component);
        componentManager.setMutator(mutator);
    }

    @Override
    public void onProps(Object props) {

    }
}
