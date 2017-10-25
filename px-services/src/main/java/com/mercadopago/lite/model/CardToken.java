package com.mercadopago.lite.model;
import android.content.Context;

import java.util.Calendar;

public class CardToken {

    private final static Calendar now = Calendar.getInstance();

    private Cardholder cardholder;
    private String cardNumber;
    private Device device;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String securityCode;

    public static Calendar getNow() {
        return now;
    }

    public Cardholder getCardholder() {
        return cardholder;
    }

    public void setCardholder(Cardholder cardholder) {
        this.cardholder = cardholder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Context context) {
        this.device = new Device(context);
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
