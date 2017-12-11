package com.mercadopago.core;

import android.support.annotation.NonNull;

import com.mercadopago.preferences.DecorationPreference;

public class PreferenceStore {

    private static PreferenceStore INSTANCE;

    private DecorationPreference decorationPreference;

    private PreferenceStore() {
    }

    public static PreferenceStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceStore();
        }
        return INSTANCE;
    }

    public DecorationPreference getDecorationPreference() {
        return decorationPreference;
    }

    public void setDecorationPreference(@NonNull final DecorationPreference decorationPreference) {
        this.decorationPreference = decorationPreference;
    }
}