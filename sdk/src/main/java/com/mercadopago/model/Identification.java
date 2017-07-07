package com.mercadopago.model;

import com.mercadopago.util.TextUtils;

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
        boolean validate =validateIdentificationType() && validateIdentificationNumber();
        return validate;
    }

    public boolean validateIdentificationType() {
        boolean validate = !TextUtils.isEmpty(type);
        return validate;
    }

    public boolean validateIdentificationNumber() {
        return (!validateIdentificationType()) ? false : !TextUtils.isEmpty(number);
    }

}
