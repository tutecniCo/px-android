package com.mercadopago.model;


/**
 * Created by mromar on 10/20/17.
 */

public class Setting {

    private Bin bin;
    private CardNumber cardNumber;
    private SecurityCode securityCode;

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
    }

    public SecurityCode getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(SecurityCode securityCode) {
        this.securityCode = securityCode;
    }
}
