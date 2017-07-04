package com.mercadopago.providers;

import android.content.Context;

import com.mercadopago.AdditionalStepVaultActivity;
import com.mercadopago.R;

/**
 * Created by marlanti on 3/23/17.
 */

public class AdditionalStepVaultProviderImpl implements AdditionalStepVaultProvider{

    Context mContext;

    public AdditionalStepVaultProviderImpl(Context context){
        this.mContext = context;
    }


    public String getInvalidAdditionalStepErrorMessage() {
        return mContext.getResources().getString(R.string.error_invalid_additional_step);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
