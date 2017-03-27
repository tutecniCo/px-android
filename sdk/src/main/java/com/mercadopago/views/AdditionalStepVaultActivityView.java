package com.mercadopago.views;

import com.mercadopago.mvp.MvpView;

/**
 * Created by marlanti on 3/23/17.
 */

public interface AdditionalStepVaultActivityView extends MvpView{
    void onInvalidStart(String msg);
    void onValidStart();
    void startEntityTypeStep();
    void startIdentificationStep();
    void startFinancialInstitutionsStep();
    void finishWithResult();
    void finishWithCancel();
}
