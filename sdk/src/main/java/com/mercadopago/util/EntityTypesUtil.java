package com.mercadopago.util;

import android.content.Context;

import com.mercadopago.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marlanti on 3/21/17.
 */

public class EntityTypesUtil {

    private static final String DEFAULT_SITE = "DEFAULT";
    private static final String INVIVIDUAL_FOR_PAYMENT = "individual";
    private static final String ASSOCIATION_FOR_PAYMENT = "association";
    private static Map<String,Integer> entityTypesResourceids;
    private static Map<String, String> entityTypesConversionsForPayment;

    public static List<String> getEntityTypesBySite(String siteId, Context context) {

        int resourceId = getResourceId(siteId, context);

        List<String> entityTypes = new ArrayList<>();
        Collections.addAll(entityTypes, context.getResources().getStringArray(resourceId));

        return entityTypes;
    }

    private static int getResourceId(String siteId, Context context){

        loadEntityTypesResourceIds();

        Integer resourceId = entityTypesResourceids.get(siteId);

        if(resourceId==null){
            resourceId = entityTypesResourceids.get(DEFAULT_SITE);
        }

        return resourceId;
    }

    private static void loadEntityTypesResourceIds(){

        if (entityTypesResourceids==null || entityTypesResourceids.isEmpty()){
            entityTypesResourceids = new HashMap<>();
            entityTypesResourceids.put(SitesUtil.MCO, R.array.entity_types_mco_array);
            entityTypesResourceids.put(DEFAULT_SITE, R.array.entity_types_default_array);
        }
    }

    private static void loadEntityTypesConversionsForPayment(Context context){

        if (entityTypesConversionsForPayment==null || entityTypesConversionsForPayment.isEmpty()){
            entityTypesConversionsForPayment = new HashMap<>();
            entityTypesConversionsForPayment.put(context.getResources().getString(R.string.entity_type_individual), INVIVIDUAL_FOR_PAYMENT);
            entityTypesConversionsForPayment.put(context.getResources().getString(R.string.entity_type_association), ASSOCIATION_FOR_PAYMENT);
        }
    }


    public static String convertForPayment(String entityType,Context context){

        loadEntityTypesConversionsForPayment(context);
        return entityTypesConversionsForPayment.get(entityType);
    }

}

