package com.mercadopago.hooks;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

public class HookComponent extends Component<HookComponent.Props> {

    public HookComponent(@NonNull HookComponent.Props props, @NonNull ActionDispatcher dispatcher) {
        super(props, dispatcher);
    }


    @Override
    public void applyProps(@NonNull Props props) {
        Log.d("log", props.toString());
    }

    public static class Props {
        public final HooksStore store;
        public String message;

        public Props(HooksStore store, String message) {
            this.store = store;
            this.message = message;
        }

        public HooksStore getStore() {
            return store;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
