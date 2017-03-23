package com.mercadopago.util;

import android.content.Context;
import android.content.Intent;

import com.mercadopago.R;
import com.mercadopago.model.Site;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marlanti on 3/21/17.
 */

public class EntityTypesUtil {

    private static Map<String,Integer> entityTypesResourceids;

    public static List<String> getEntityTypesBySite(String siteId, Context context) {

        int resourceId = getResourceId(siteId);

        List<String> entityTypes = new ArrayList<>();
        Collections.addAll(entityTypes, context.getResources().getStringArray(resourceId));

        return entityTypes;
    }

    private static int getResourceId(String siteId){

        loadEntityTypesResourceIds();

        Integer resourceId = entityTypesResourceids.get(siteId);

        if(resourceId==null){
            resourceId = R.array.entity_types_default_array;
        }

        return resourceId;
    }

    private static void loadEntityTypesResourceIds(){

        if (entityTypesResourceids==null || entityTypesResourceids.isEmpty()){
            entityTypesResourceids = new HashMap<>();
            entityTypesResourceids.put(SitesUtil.MCO, R.array.entity_types_array);
        }
    }

}

