package com.mercadopago.model;

import java.math.BigDecimal;

/**
 * Created by mromar on 3/13/17.
 */

public class DiscountCard {

    private Long id;
    private Discount discount;
    private String cardHolderName;
    private Long lastFourCardNumbers;

    public Long getId() {
        return this.id;
    }

    public Discount getDiscount() {
        return this.discount;
    }
}
