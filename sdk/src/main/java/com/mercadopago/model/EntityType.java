package com.mercadopago.model;

/**
 * Created by marlanti on 4/18/17.
 */

public class EntityType {

    private String id;
    private String name;

    public EntityType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
