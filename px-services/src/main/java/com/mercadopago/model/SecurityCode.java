package com.mercadopago.model;

/**
 * Created by mromar on 10/20/17.
 */

public class SecurityCode {

    private String cardLocation;
    private Integer length;
    private String mode;

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}