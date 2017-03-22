package com.mercadopago.model;

/**
 * Created by mromar on 6/3/16.
 */
public class Site {

    private String id;
    private String currencyId;

    public Site(String id, String currencyId) {
        this.id = id;
        this.currencyId = currencyId;
    }

    public String getId(){
        return id;
    }

    public String getCurrencyId(){
        return currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (!id.equals(site.id)) return false;
        return currencyId.equals(site.currencyId);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + currencyId.hashCode();
        return result;
    }
}
