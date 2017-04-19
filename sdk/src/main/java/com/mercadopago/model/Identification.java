package com.mercadopago.model;

import android.text.TextUtils;

import java.io.Serializable;

public class Identification implements Serializable {

    private String number;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean validateIdentification() {
        return validateIdentificationType() && validateIdentificationNumber();
    }

    public boolean validateIdentificationType() {
        return !TextUtils.isEmpty(type);
    }

    public boolean validateIdentificationNumber() {
        return (!validateIdentificationType()) ? false : !TextUtils.isEmpty(number);
    }

}
