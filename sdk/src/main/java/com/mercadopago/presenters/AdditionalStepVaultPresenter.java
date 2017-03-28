package com.mercadopago.presenters;

import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Site;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.providers.AdditionalStepVaultProviderImpl;
import com.mercadopago.statemachines.AdditionalStepVaultStateMachine;
import com.mercadopago.views.AdditionalStepVaultActivityView;

/**
 * Created by marlanti on 3/23/17.
 */

public class AdditionalStepVaultPresenter extends MvpPresenter<AdditionalStepVaultActivityView, AdditionalStepVaultProviderImpl> {


    private Site mSite;
    private PaymentMethod mPaymentMethod;
    private String mPublicKey;
    private AdditionalStepVaultStateMachine state;


    public void setSite(Site mSite) {
        this.mSite = mSite;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }

    public void validateActivityParameters() throws IllegalStateException {
        if (mPaymentMethod == null) {
            getView().onInvalidStart("payment method is null");
        } else if (mPublicKey == null) {
            getView().onInvalidStart("public key not set");
        } else if (mSite == null) {
            getView().onInvalidStart("site not set");
        } else {
            getView().onValidStart();
        }
    }

    public void checkFlow() {

        if (isOnlyIdentificationRequired()) {
            state = AdditionalStepVaultStateMachine.IDENTIFICATION;
            state.onInit(this);
        } else if (isEntityTypeStepRequired()) {
            state = AdditionalStepVaultStateMachine.IDENTIFICATION;
            state.onInit(this);
        } else if (isFinancialInstitutionsStepRequired()) {
            state = AdditionalStepVaultStateMachine.FINANCIAL_INSTITUTIONS;
            state.onInit(this);


        } else {
            getView().onInvalidStart("No additional step found");
        }


    }

    private boolean isOnlyIdentificationRequired() {
        return !isEntityTypeRequired() && !mPaymentMethod.isOnPaymentMethod() && isIdentificationRequired();
    }

    private boolean isIdentificationRequired() {
        return mPaymentMethod.isIdentificationNumberRequired() && mPaymentMethod.isIdentificationTypeRequired();
    }


    public boolean isEntityTypeStepRequired() {
        return isEntityTypeRequired();
    }


    public boolean isFinancialInstitutionsStepRequired() {

        if (isPaymentMethodSelected()) {
            return mPaymentMethod.isFinancialInstitutionsRequired();
        }
        return false;
    }


    private boolean isEntityTypeRequired() {
        if (isPaymentMethodSelected()) {
            return mPaymentMethod.isEntityTypeRequired();
        }

        return false;
    }

    private boolean isPaymentMethodSelected() {
        return mPaymentMethod != null;
    }

    public Site getSite() {
        return mSite;
    }

    public PaymentMethod getmPaymentMethod() {
        return mPaymentMethod;
    }

    public String getmPublicKey() {
        return mPublicKey;
    }


    public void checkFlowWithIdentificationSelected() {
        state = state.onNextStep(this);
    }

    public void checkFlowWithEntityTypeSelected() {
        state = state.onNextStep(this);
    }

    public void checkFlowWithFinancialInstitutionSelected() {
        state = state.onNextStep(this);
    }

    public void onBackPressed() {
        state = state.onBackPressed(this);
    }

    public void startFinancialInstitutionsStep() {
        getView().startFinancialInstitutionsStep();
    }

    public void startEntityTypeStep() {
        getView().startEntityTypeStep();
    }

    public void finishWithCancel() {
        getView().finishWithCancel();
    }

    public void startIdentificationStep() {
        getView().startIdentificationStep();
    }

    public boolean isOnlyIdentificationAndFinancialStepRequired() {
        return isFinancialInstitutionsStepRequired() && isIdentificationRequired() && !isEntityTypeStepRequired();
    }

    public void onError(String message) {
        getView().onError(message);
    }

    public String getInvalidAdditionalStepErrorMessage() {
        return getResourcesProvider().getInvalidAdditionalStepErrorMessage();
    }

    public void finishWithResult() {
        getView().finishWithResult();
    }
}
