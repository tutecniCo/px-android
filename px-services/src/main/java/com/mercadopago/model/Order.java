package com.mercadopago.model;

/**
 * Created by mromar on 10/20/17.
 */

public class Order {

    private Long id;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
