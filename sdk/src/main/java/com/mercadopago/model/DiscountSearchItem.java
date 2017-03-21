package com.mercadopago.model;

import com.mercadopago.util.TextUtil;

import java.math.BigDecimal;

import static com.mercadopago.util.TextUtils.isEmpty;

/**
 * Created by mromar on 3/13/17.
 */

public class DiscountSearchItem {

    private static final String TYPE_DISCOUNT = "discount";
    private static final String TYPE_DISCOUNT_CARD = "discount_card";
    private static final String TYPE_GROUP = "group";

    private Long id;
    private String type;
    private String description;
    private BigDecimal couponAmount;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Boolean hasDescription() {
        return !isEmpty(this.description);
    }

    public boolean isGroup() {
        return type != null && type.equals(TYPE_GROUP);
    }

    public boolean isDiscountType() {
        return type != null && type.equals(TYPE_DISCOUNT);
    }

    public boolean isDiscountCard() {
        return type != null && type.equals(TYPE_DISCOUNT_CARD);
    }
}
