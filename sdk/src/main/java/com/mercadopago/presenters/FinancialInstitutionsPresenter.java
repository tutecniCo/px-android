package com.mercadopago.presenters;

import android.content.Context;

import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.providers.FinancialInstitutionsProvider;
import com.mercadopago.views.FinancialInstitutionsActivityView;

import java.util.List;

/**
 * Created by marlanti on 3/13/17.
 */

public class FinancialInstitutionsPresenter extends MvpPresenter<FinancialInstitutionsActivityView, FinancialInstitutionsProvider> {

    private FailureRecovery mFailureRecovery;


    //Activity parameters
    private String mPublicKey;
    private PaymentMethod mPaymentMethod;
    private List<FinancialInstitution> mFinancialInstitutions;


    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }

    public void setFinancialInstitutions(List<FinancialInstitution> mFinancialInstitutions) {
        this.mFinancialInstitutions = mFinancialInstitutions;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }

    private void setFailureRecovery(FailureRecovery failureRecovery) {
        this.mFailureRecovery = failureRecovery;
    }


    public String getPublicKey() {
        return mPublicKey;
    }

    public PaymentMethod getPaymentMethod() {
        return this.mPaymentMethod;
    }


    public void validate() throws IllegalStateException {
        if (mPaymentMethod == null) {
            throw new IllegalStateException("payment method is null");
        }
        if (mPublicKey == null) {
            throw new IllegalStateException("public key is null");
        }
    }

    public void initialize() {
        try {
            validate();
            showFinancialInstitutions();
        } catch (IllegalStateException exception) {
            String standardErrorMessage = getResourcesProvider().getStandardErrorMessageGotten();
            getView().showError(standardErrorMessage, exception.getMessage());
        }
    }

    public void showFinancialInstitutions()
    {
        if(wereFinancialInstitutionsSet()){
            resolveFinancialInstitutions();
        }else{
            getFinancialInstitutions();
        }

    }

    private boolean wereFinancialInstitutionsSet() {
        return mFinancialInstitutions != null && !mFinancialInstitutions.isEmpty();
    }

    private void getFinancialInstitutions() {
        this.mFinancialInstitutions = getResourcesProvider().getFinancialInstitutions(mPaymentMethod);
        resolveFinancialInstitutions();
    }

    protected void resolveFinancialInstitutions() {

        if (mFinancialInstitutions == null || mFinancialInstitutions.isEmpty()) {
            String standardErrorMessage = getResourcesProvider().getStandardErrorMessageGotten();
            String errorDetail = getResourcesProvider().getEmptyFinancialInstitutionsErrorMessage();
            getView().showError(standardErrorMessage, errorDetail);
        }
        if (mFinancialInstitutions.size() == 1) {
            getView().finishWithResult(mFinancialInstitutions.get(0));
        } else {
            getView().initialize();
            getView().showTimer();
            getView().trackScreen();
            String title = getResourcesProvider().getFinancialInstitutionsTitle();
            getView().showHeader(title);
            getView().showFinancialInstitutions(mFinancialInstitutions,getDpadSelectionCallback());

        }
    }

    public void recoverFromFailure() {
        if (mFailureRecovery != null) {
            mFailureRecovery.recover();
        }
    }

    public void onItemSelected(int position) {
        getView().finishWithResult(mFinancialInstitutions.get(position));
    }

    private OnSelectedCallback<Integer> getDpadSelectionCallback() {
        return new OnSelectedCallback<Integer>() {
            @Override
            public void onSelected(Integer position) {
                onItemSelected(position);
            }
        };
    }

}
