package com.mercadopago.providers;

import android.content.Context;

import com.mercadopago.model.Site;
import com.mercadopago.util.EntityTypesUtil;

import java.util.List;

/**
 * Created by marlanti on 3/21/17.
 */

public class EntityTypeProviderImpl implements EntityTypeProvider{

    private Context mContext;

    public EntityTypeProviderImpl(Context context){
        this.mContext = context;
    }

    @Override
    public List<String> getEntityTypesBySite(Site site) {

        return EntityTypesUtil.getEntityTypesBySite(site.getId(),mContext);
    }
}
