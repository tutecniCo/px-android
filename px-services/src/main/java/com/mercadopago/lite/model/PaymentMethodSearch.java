package com.mercadopago.lite.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mromar on 10/20/17.
 */

public class PaymentMethodSearch {

    @SerializedName("groups")
    private List<PaymentMethodSearchItem> paymentMethodSearchItems;
    @SerializedName("custom_options")
    private List<CustomOptionSearchItem> customOptionSearchItems;
    private List<PaymentMethod> paymentMethods;
    private List<Card> cards;
    private PaymentMethodSearchItem defaultOption;

    public List<PaymentMethodSearchItem> getPaymentMethodSearchItems() {
        return paymentMethodSearchItems;
    }

    public void setPaymentMethodSearchItems(List<PaymentMethodSearchItem> paymentMethodSearchItems) {
        this.paymentMethodSearchItems = paymentMethodSearchItems;
    }

    public List<CustomOptionSearchItem> getCustomOptionSearchItems() {
        return customOptionSearchItems;
    }

    public void setCustomOptionSearchItems(List<CustomOptionSearchItem> customOptionSearchItems) {
        this.customOptionSearchItems = customOptionSearchItems;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public PaymentMethodSearchItem getDefaultOption() {
        return defaultOption;
    }

    public void setDefaultOption(PaymentMethodSearchItem defaultOption) {
        this.defaultOption = defaultOption;
    }
}
