package com.mercadopago.views;

import com.mercadopago.mvp.MvpView;

/**
 * Created by marlanti on 3/23/17.
 */

public interface AdditionalStepVaultView extends MvpView {
    void onInvalidStart(String msg);

    void onValidStart();

    void startEntityTypeStep();

    void startIdentificationStep();

    void startFinancialInstitutionsStep();

    void startIdentificationStepAnimatedBack();

    void startEntityTypeStepAnimatedBack();

    void startFinancialInstitutionsStepAnimatedBack();

    void finishWithResult();

    void finishWithCancel();

    void onError(String message);

    void animateNextSelection();

    void animateBackSelection();
}
