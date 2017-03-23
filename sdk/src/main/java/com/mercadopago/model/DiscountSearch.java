package com.mercadopago.model;

import java.util.List;

/**
 * Created by mromar on 3/13/17.
 */

public class DiscountSearch {

    private List<DiscountSearchItem> groups;

    private List<Discount> discounts;

    private List<DiscountCard> discountsCards;

    public List<DiscountSearchItem> getGroups() {
        return this.groups;
    }

    public List<Discount> getDiscounts() {
        return this.discounts;
    }

    public List<DiscountCard> getDiscountsCards() {
        return this.discountsCards;
    }


}
