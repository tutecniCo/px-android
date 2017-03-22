package com.mercadopago.providers;

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.util.StepManagerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marlanti on 3/21/17.
 */

public class EntityTypeProviderImpl implements EntityTypeProvider{

    private Context mContext;

    public EntityTypeProviderImpl(Context context){
        this.mContext = context;
    }

    //TODO ojo se debería contemplar en el  strings.xml por país. Esto está ok?
    @Override
    public List<String> getEntityTypes() {
        List<String> entityTypes = new ArrayList<>();
        Collections.addAll(entityTypes, mContext.getResources().getStringArray(R.array.entity_types_array));

        return entityTypes;
    }
}
