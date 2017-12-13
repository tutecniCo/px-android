package com.mercadopago.core;

import android.support.annotation.NonNull;

import com.mercadopago.model.PaymentMethod;
import com.mercadopago.plugins.PaymentMethodPlugin;
import com.mercadopago.plugins.model.PaymentMethodInfo;
import com.mercadopago.preferences.DecorationPreference;

import java.util.ArrayList;
import java.util.List;

public class CheckoutSessionStore {

    private static CheckoutSessionStore INSTANCE;

    private DecorationPreference decorationPreference;
    private List<PaymentMethodPlugin> paymentMethodPluginList = new ArrayList<>();
    

    private CheckoutSessionStore() {
    }

    public static CheckoutSessionStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CheckoutSessionStore();
        }
        return INSTANCE;
    }

    public DecorationPreference getDecorationPreference() {
        return decorationPreference;
    }

    public void setDecorationPreference(@NonNull final DecorationPreference decorationPreference) {
        this.decorationPreference = decorationPreference;
    }

    public List<PaymentMethodPlugin> getPaymentMethodPluginList() {
        return paymentMethodPluginList;
    }

    public void setPaymentMethodPluginList(List<PaymentMethodPlugin> paymentMethodPluginList) {
        this.paymentMethodPluginList = paymentMethodPluginList;
    }
}
