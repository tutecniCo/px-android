package com.mercadopago.presenters;

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.core.MercadoPagoServices;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.views.FinancialInstitutionsActivityView;

import java.util.List;

/**
 * Created by marlanti on 3/13/17.
 */

public class FinancialInstitutionsPresenter {

    private FinancialInstitutionsActivityView mView;
    private Context mContext;
    private FailureRecovery mFailureRecovery;


    //Activity parameters
    private String mPublicKey;
    private PaymentMethod mPaymentMethod;
    private String mPrivateKey;
    private List<FinancialInstitution> mFinancialInstitutions;
    private MercadoPagoServices mMercadoPago;


    public FinancialInstitutionsPresenter(Context context) {
        this.mContext = context;
    }

    public void setView(FinancialInstitutionsActivityView view) {
        this.mView = view;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }

    public void setFinancialInstitutions(List<FinancialInstitution> mFinancialInstitutions) {
        this.mFinancialInstitutions = mFinancialInstitutions;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.mPrivateKey = privateKey;
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


    public void validateActivityParameters() throws IllegalStateException {
        if (mPaymentMethod == null) {
            mView.onInvalidStart("payment method is null");
        } else {
            mView.onValidStart();
        }
    }

    public void initializeMercadoPago() {
        mMercadoPago = new MercadoPagoServices.Builder()
                .setContext(mContext)
                .setPublicKey(mPublicKey)
                .setPrivateKey(mPrivateKey)
                .build();
    }

    public void loadFinancialInstitutions() {
        if (wereFinancialInstitutionsSet()) {
            resolveFinancialInstitutions(mFinancialInstitutions);
        } else {
            mView.startErrorView(mContext.getString(R.string.mpsdk_standard_error_message),
                    "no financialInstitutions found at FinancialInstitutionsActivity");
        }
    }

    private boolean wereFinancialInstitutionsSet() {
        return mFinancialInstitutions != null;
    }



    protected void resolveFinancialInstitutions(List<FinancialInstitution> financialInstitutions) {

        mFinancialInstitutions = financialInstitutions;
        if (mFinancialInstitutions.isEmpty()) {
            mView.startErrorView(mContext.getString(R.string.mpsdk_standard_error_message),
                    "no financialInstitutions found at FinancialInstitutionsActivity");
        } else if (mFinancialInstitutions.size() == 1) {
            mView.finishWithResult(financialInstitutions.get(0));
        } else {
            mView.showHeader();
            mView.initializeFinancialInstitutions(financialInstitutions);
        }
    }


    public void recoverFromFailure() {
        if (mFailureRecovery != null) {
            mFailureRecovery.recover();
        }
    }

    public void onItemSelected(int position) {
        mView.finishWithResult(mFinancialInstitutions.get(position));
    }

}
