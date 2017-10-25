package com.mercadopago.hooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.R;
import com.mercadopago.components.ComponentManager;

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
//        setContentView(R.layout.mpsdk_activity_hook);

//        final Mutator mutator = new Mutator() {
//            @Override
//            public void setPropsListener(@NonNull final MutatorPropsListener listener) {
//
//            }
//        };

        componentManager = new ComponentManager(this);

        final Hook hook = HooksStore.getInstance().getHook();

        if (hook == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

//        componentManager.setActionsListener(presenter);
        componentManager.setComponent(hook.component);
//        componentManager.setMutator(mutator);
    }
}
