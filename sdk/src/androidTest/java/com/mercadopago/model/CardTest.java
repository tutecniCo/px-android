package com.mercadopago.model;

import com.mercadopago.CheckoutActivity;
import com.mercadopago.test.BaseTest;
import com.mercadopago.test.StaticMock;

public class CardTest extends BaseTest<CheckoutActivity> {

    public CardTest() {
        super(CheckoutActivity.class);
    }

    public void testIsSecurityCodeRequired() {

        com.mercadopago.model.Card card = StaticMock.getCard();

        assertTrue(card.isSecurityCodeRequired());
    }

    public void testIsSecurityCodeRequiredNull() {

        com.mercadopago.model.Card card = StaticMock.getCard();
        card.setSecurityCode(null);
        assertTrue(!card.isSecurityCodeRequired());
    }

    public void testIsSecurityCodeRequiredLengthZero() {

        com.mercadopago.model.Card card = StaticMock.getCard();
        card.getSecurityCode().setLength(0);
        assertTrue(!card.isSecurityCodeRequired());
    }
}
