package com.mercadopago.hooks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mercadopago.components.Component;
import com.mercadopago.components.NextAction;
import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public abstract class HookComponent extends Component<HookComponent.Props> {

    public HookComponent(@NonNull final Props props) {
        super(props);
    }

    public static class Props {

        public final HooksStore store;
        public final String paymentTypeId;
        public final PaymentData paymentData;
        public final DecorationPreference decorationPreference;
        public final String toolbarTitle;
        public final boolean toolbarVisibile;

        public Props(@NonNull final HooksStore store,
                     @Nullable final String paymentTypeId,
                     @Nullable final PaymentData paymentData,
                     @NonNull final DecorationPreference decorationPreference,
                     @NonNull final String toolbarTitle,
                     final boolean toolbarVisibile) {
            this.store = store;
            this.paymentTypeId = paymentTypeId;
            this.paymentData = paymentData;
            this.decorationPreference = decorationPreference;
            this.toolbarTitle = toolbarTitle;
            this.toolbarVisibile = toolbarVisibile;
        }
    }

    public void onContinue() {
        getDispatcher().dispatch(new NextAction());
    }
}