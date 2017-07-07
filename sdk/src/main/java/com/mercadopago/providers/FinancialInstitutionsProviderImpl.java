package com.mercadopago.providers;

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;

import java.util.List;

/**
 * Created by marlanti on 7/7/17.
 */

public class FinancialInstitutionsProviderImpl implements FinancialInstitutionsProvider {

    private Context mContext;

    public FinancialInstitutionsProviderImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public String getStandardErrorMessageGotten() {
        return mContext.getString(R.string.mpsdk_standard_error_message);
    }

    @Override
    public String getFinancialInstitutionsTitle() {
        return mContext.getString(R.string.mpsdk_financial_institutions_title);
    }

    @Override
    public List<FinancialInstitution> getFinancialInstitutions(PaymentMethod paymentMethod) {
        return paymentMethod != null ? paymentMethod.getFinancialInstitutions() : null;
    }

    @Override
    public String getEmptyFinancialInstitutionsErrorMessage() {
        return mContext.getString(R.string.mpsdk_financial_institutions_not_found_error_message);
    }
}
