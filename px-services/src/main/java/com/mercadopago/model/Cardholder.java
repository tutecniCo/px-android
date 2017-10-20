package com.mercadopago.model;

/**
 * Created by mromar on 10/20/17.
 */

public class Cardholder {

    private Identification identification;
    private String name;

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
