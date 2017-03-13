package com.mercadopago.model;

import java.math.BigDecimal;

/**
 * Created by mromar on 3/13/17.
 */

public class DiscountsCards {

    private Long id;
    private String name;
    private BigDecimal percentOff;
    private BigDecimal amountOff;
    private BigDecimal couponAmount;
    private String currencyId;
    private String couponCode;
    private String cardHolderName;
    private Long lastFourCardNumbers;
}
