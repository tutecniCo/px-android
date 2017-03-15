package com.mercadopago.views;

import com.mercadopago.model.ApiException;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.Issuer;

import java.util.List;

/**
 * Created by marlanti on 3/14/17.
 */

public interface FinancialInstitutionsActivityView {

    void onValidStart();
    void onInvalidStart(String message);
    void initializeFinancialInstitutions(List<FinancialInstitution> financialInstitutionList);
    void showApiExceptionError(ApiException exception);
    void startErrorView(String message, String errorDetail);
    void showHeader();
    void showLoadingView();
    void stopLoadingView();
    void finishWithResult(FinancialInstitution financialInstitution);
}
