package com.mercadopago.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mromar on 3/13/17.
 */

public class DiscountSearch {

    private List<DiscountSearchItem> groups;

    private List<Discount> discounts;

    private List<DiscountsCards> discountsCards;

    public List<DiscountSearchItem> getGroups() {
        return groups;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }


}
