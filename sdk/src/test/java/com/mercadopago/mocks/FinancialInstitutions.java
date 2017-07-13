package com.mercadopago.mocks;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.utils.ResourcesUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by marlanti on 7/12/17.
 */

public class FinancialInstitutions {

    public static List<FinancialInstitution> getFinancialInstitutions() {
        List<FinancialInstitution> financialInstitutionList;
        String json = ResourcesUtil.getStringResource("financial_institutions.json");

        try {
            Type listType = new TypeToken<List<FinancialInstitution>>() {
            }.getType();
            financialInstitutionList = JsonUtil.getInstance().getGson().fromJson(json, listType);
        } catch (Exception ex) {
            financialInstitutionList = null;
        }
        return financialInstitutionList;
    }
}
