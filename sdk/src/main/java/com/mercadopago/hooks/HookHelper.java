package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentData;
import com.mercadopago.preferences.DecorationPreference;

public class HookHelper {

    public static Hook activateBeforePaymentMethodConfig(@NonNull final CheckoutHooks checkoutHooks,
                                                  @NonNull final String typeId,
                                                  @NonNull final DecorationPreference preference) {
        Hook hook = null;

        if (checkoutHooks != null) {
            hook = checkoutHooks.beforePaymentMethodConfig(typeId, preference);
            if (hook != null && !hook.isEnabled()) {
                hook = null;
            }
        }

        return hook;
    }

    public static Hook activateAfterPaymentMethodConfig(@NonNull final CheckoutHooks checkoutHooks,
                                                 @NonNull final PaymentData paymentData,
                                                 @NonNull final DecorationPreference preference) {
        Hook hook = null;

        if (checkoutHooks != null) {
            hook = checkoutHooks.afterPaymentMethodConfig(paymentData, preference);
            if (hook != null && !hook.isEnabled()) {
                hook = null;
            }
        }

        return hook;
    }

    public static Hook activateBeforePayment(@NonNull final CheckoutHooks checkoutHooks,
                                      @NonNull final PaymentData paymentData,
                                      @NonNull final DecorationPreference preference) {
        Hook hook = null;

        if (checkoutHooks != null) {
            hook = checkoutHooks.beforePayment(paymentData, preference);
            if (hook != null && !hook.isEnabled()) {
                hook = null;
            }
        }

        return hook;
    }
}