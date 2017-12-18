package com.mercadopago.core;

import android.support.annotation.NonNull;

import com.mercadopago.hooks.CheckoutHooks;
import com.mercadopago.hooks.Hook;
import com.mercadopago.plugins.PaymentMethodPlugin;
import com.mercadopago.plugins.model.PaymentMethodInfo;
import com.mercadopago.preferences.DecorationPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutStore {

    private static final CheckoutStore INSTANCE = new CheckoutStore();

    //Read only data
    private DecorationPreference decorationPreference;
    private List<PaymentMethodPlugin> paymentMethodPluginList = new ArrayList<>();
    private CheckoutHooks checkoutHooks;
    private Hook hook;
    private Map<String, Object> data = new HashMap();

    //App state
    private PaymentMethodInfo selectedPaymentMethod;


    private CheckoutStore() {
    }

    public static CheckoutStore getInstance() {
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

    public PaymentMethodPlugin getPaymentMethodPluginById(@NonNull final String id) {
        for (PaymentMethodPlugin plugin : paymentMethodPluginList) {
            if (plugin.getPaymentMethodInfo().id.equalsIgnoreCase(id)) {
                return plugin;
            }
        }
        return null;
    }

    public PaymentMethodInfo getPaymentMethodPluginInfoById(@NonNull final String id) {
        for (PaymentMethodPlugin plugin : paymentMethodPluginList) {
            final PaymentMethodInfo info = plugin.getPaymentMethodInfo();
            if (info.id.equalsIgnoreCase(id)) {
                return info;
            }
        }
        return null;
    }

    public void setPaymentMethodPluginList(@NonNull final List<PaymentMethodPlugin> paymentMethodPluginList) {
        this.paymentMethodPluginList = paymentMethodPluginList;
    }

    public PaymentMethodInfo getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }

    public void setSelectedPaymentMethod(PaymentMethodInfo selectedPaymentMethod) {
        this.selectedPaymentMethod = selectedPaymentMethod;
    }

    public Hook getHook() {
        return hook;
    }

    public void setHook(Hook hook) {
        this.hook = hook;
    }

    public CheckoutHooks getCheckoutHooks() {
        return checkoutHooks;
    }

    public void setCheckoutHooks(CheckoutHooks checkoutHooks) {
        this.checkoutHooks = checkoutHooks;
    }

    public Map<String, Object> getData() {
        return data;
    }
}