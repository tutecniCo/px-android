package com.mercadopago.presenters;

/**
 * Created by marlanti on 3/3/17.
 */

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.callbacks.Callback;
import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.controllers.PaymentMethodGuessingController;
import com.mercadopago.core.MercadoPagoServices;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.Issuer;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.preferences.PaymentPreference;
import com.mercadopago.uicontrollers.card.FrontCardView;
import com.mercadopago.views.EntityTypeActivityView;
import com.mercadopago.views.IssuersActivityView;

import java.util.ArrayList;
import java.util.List;


public class EntityTypePresenter {

    private EntityTypeActivityView mView;
    private Context mContext;
    private FailureRecovery mFailureRecovery;


    //Activity parameters
    private String mPublicKey;
    private PaymentMethod mPaymentMethod;
    private Identification mIdentification;
    private List<String> mEntityTypes;
    private IdentificationType mIdentificationType;


    public EntityTypePresenter(Context context) {
        this.mContext = context;
    }

    public void setView(EntityTypeActivityView view) {
        this.mView = view;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }


    private void setFailureRecovery(FailureRecovery failureRecovery) {
        this.mFailureRecovery = failureRecovery;
    }

    public boolean isIdentificationAvailable() {
        return mIdentification != null && mPaymentMethod != null;
    }

    public Identification getIdentification() {
        return mIdentification;
    }

    public IdentificationType getIdentificationType() {
        return mIdentificationType;
    }

    public String getPublicKey() {
        return mPublicKey;
    }

    public PaymentMethod getPaymentMethod() {
        return this.mPaymentMethod;
    }

    public Integer getIdentificationLength() {
        return mIdentification.getNumber().length();
    }

    public void validateActivityParameters() throws IllegalStateException {
        if (mPaymentMethod == null) {
            mView.onInvalidStart("payment method is null");
        } else if (mIdentification == null) {
            if (mPublicKey == null) {
                mView.onInvalidStart("public key not set");
            } else {
                mView.onValidStart();
            }
        } else {
            mView.onValidStart();
        }
    }


    public void loadEntityTypes() {
        if (wereEntityTypesSet()) {
            resolveEntityTypes(mEntityTypes);
        } else {
            getEntityTypes();
        }
    }

    private boolean wereEntityTypesSet() {
        return mEntityTypes != null;
    }

    private void getEntityTypes() {


        List<String> list = new ArrayList<>();
        list.add("Natural");
        list.add("Juridica");
        this.mEntityTypes = list;
        resolveEntityTypes(list);
    }


    protected void resolveEntityTypes(List<String> entityTypes) {

        mEntityTypes = entityTypes;
        if (mEntityTypes.isEmpty()) {
            mView.startErrorView(mContext.getString(R.string.mpsdk_standard_error_message),
                    "no entityTypes found at EntityTypesActivity");
        } else if (mEntityTypes.size() == 1) {
            mView.finishWithResult(entityTypes.get(0));
        } else {
            mView.showHeader();
            mView.initializeEntityTypes(entityTypes);

        }


    }

    public void recoverFromFailure() {
        if (mFailureRecovery != null) {
            mFailureRecovery.recover();
        }
    }

    public void onItemSelected(int position) {
        mView.finishWithResult(mEntityTypes.get(position));
    }

    public void setIdentification(Identification identification) {
        this.mIdentification = identification;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
    }
}
