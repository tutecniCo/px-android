package com.mercadopago.constants;

import com.mercadopago.model.Site;

/**
 * Created by mromar on 6/3/16.
 */
public class Sites {

    public static final Site ARGENTINA = new Site("MLA", "ARS");
    public static final Site BRASIL = new Site("MLB", "BRL");
    public static final Site CHILE = new Site("MLC", "CLP");
    public static final Site MEXICO = new Site("MLM", "MXN");
    public static final Site COLOMBIA = new Site("MCO", "COP");
    public static final Site VENEZUELA = new Site("MLV", "VEF");
    public static final Site USA = new Site("USA", "USD");
    public static final Site PERU = new Site("MPE", "PEN");


    private Sites() {
    }

    public static Site getById(String siteId) {
        Site site;
        if (siteId.equals(com.mercadopago.constants.Sites.ARGENTINA.getId())) {
            site = com.mercadopago.constants.Sites.ARGENTINA;
        } else if (siteId.equals(com.mercadopago.constants.Sites.BRASIL.getId())) {
            site = com.mercadopago.constants.Sites.BRASIL;
        } else if (siteId.equals(com.mercadopago.constants.Sites.CHILE.getId())) {
            site = com.mercadopago.constants.Sites.CHILE;
        } else if (siteId.equals(com.mercadopago.constants.Sites.MEXICO.getId())) {
            site = com.mercadopago.constants.Sites.MEXICO;
        } else if (siteId.equals(com.mercadopago.constants.Sites.COLOMBIA.getId())) {
            site = com.mercadopago.constants.Sites.COLOMBIA;
        } else if (siteId.equals(com.mercadopago.constants.Sites.VENEZUELA.getId())) {
            site = com.mercadopago.constants.Sites.VENEZUELA;
        } else if (siteId.equals(com.mercadopago.constants.Sites.USA.getId())) {
            site = com.mercadopago.constants.Sites.USA;
        } else if (siteId.equals(com.mercadopago.constants.Sites.PERU.getId())) {
            site = com.mercadopago.constants.Sites.PERU;
        } else {
            site = null;
        }
        return site;
    }
}
