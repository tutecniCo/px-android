package com.mercadopago.util;

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.constants.Sites;
import com.mercadopago.model.EntityType;
import com.mercadopago.model.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marlanti on 3/21/17.
 */

public class EntityTypesUtil {

    private static final String INDIVIDUAL_FOR_PAYMENT = "individual";
    private static final String ASSOCIATION_FOR_PAYMENT = "association";
    private static Map<String, List<EntityType>> entityTypesBySite;

    public static List<EntityType> getEntityTypesBySite(Site site, Context context) {

        loadEntityTypesBySite(context);

        List<EntityType> list = entityTypesBySite.get(site.getId());

        return list == null ? new ArrayList<EntityType>() : list;
    }


    private static void loadEntityTypesBySite(Context context) {

        if (entityTypesBySite == null || entityTypesBySite.isEmpty()) {
            entityTypesBySite = new HashMap<>();

            //MCO
            List<EntityType> entityTypesMCO = new ArrayList<>();

            entityTypesMCO.add(new EntityType(INDIVIDUAL_FOR_PAYMENT, context.getResources().getString(R.string.entity_type_mco_individual)));
            entityTypesMCO.add(new EntityType(ASSOCIATION_FOR_PAYMENT, context.getResources().getString(R.string.entity_type_mco_association)));

            entityTypesBySite.put(Sites.COLOMBIA.getId(), entityTypesMCO);

        }

    }


}

