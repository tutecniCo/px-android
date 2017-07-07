package com.mercadopago.views;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.mvp.MvpView;

import java.util.List;

/**
 * Created by marlanti on 3/14/17.
 */

public interface FinancialInstitutionsActivityView extends MvpView {

    void showError(String message, String errorDetail);

    void showHeader(String title);

    void showLoadingView();

    void stopLoadingView();

    void finishWithResult(FinancialInstitution financialInstitution);

    void initialize();

    void showFinancialInstitutions(List<FinancialInstitution> financialInstitutionList, OnSelectedCallback<Integer> onSelectedCallback);

    void showTimer();

    void trackScreen();

}