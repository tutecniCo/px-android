package com.mercadopago.presenters;

import android.content.Context;
import android.os.ParcelUuid;
import android.text.TextUtils;

import com.mercadopago.R;
import com.mercadopago.callbacks.Callback;
import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.core.MercadoPagoServices;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.Payer;
import com.mercadopago.views.IdentificationActivityView;

import java.util.List;

import static com.mercadopago.util.TextUtil.isEmpty;


/**
 * Created by marlanti on 3/16/17.
 */

public class IdentificationPresenter {

    private static final int CARD_DEFAULT_IDENTIFICATION_NUMBER_LENGTH = 12;

    private List<IdentificationType> mIdentificationTypes;

    private IdentificationActivityView mView;
    private Context mContext;
    private FailureRecovery mFailureRecovery;

    //Mercado Pago instance
    private MercadoPagoServices mMercadoPago;

    //Activity parameters
    private String mPublicKey;

    //Card Info
    private IdentificationType mIdentificationType;
    private String mIdentificationNumber;
    private IdentificationType mSavedIdentificationType;
    private Identification mIdentification;
    private Identification mSavedIdentification;
    private Payer mPayer;

    public IdentificationPresenter(Context context) {
        this.mContext = context;
    }

    public void setView(IdentificationActivityView view) {
        this.mView = view;
    }

    public FailureRecovery getFailureRecovery() {
        return mFailureRecovery;
    }

    public void setFailureRecovery(FailureRecovery failureRecovery) {
        this.mFailureRecovery = failureRecovery;
    }

    public String getPublicKey() {
        return mPublicKey;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }


    public Identification getIdentification() {
        return mIdentification;
    }

    public void setIdentification(Identification mIdentification) {
        this.mIdentification = mIdentification;
    }

    public List<IdentificationType> getIdentificationTypes() {
        return this.mIdentificationTypes;
    }


    public void setIdentificationTypesList(List<IdentificationType> identificationTypesList) {
        this.mIdentificationTypes = identificationTypesList;
    }

    public Identification getSavedIdentification() {
        return mSavedIdentification;
    }

    public void setSavedIdentification(Identification identification) {
        this.mSavedIdentification = identification;
        this.mIdentification = identification;
        this.mIdentificationNumber = identification.getNumber();
    }

    public IdentificationType getSavedIdentificationType() {
        return mSavedIdentificationType;
    }

    public void setSavedIdentificationType(IdentificationType savedIdentificationType) {
        this.mSavedIdentificationType = savedIdentificationType;
        this.mIdentificationType = savedIdentificationType;
    }

    public void validateActivityParameters() {
        if (mPublicKey == null) {
            mView.onInvalidStart("public key not set");
        } else {
            mView.onValidStart();
        }
    }

    public void initializeMercadoPago() {
        if (mPublicKey == null) {
            return;
        }

        mMercadoPago = new MercadoPagoServices.Builder()
                .setContext(mContext)
                .setPublicKey(mPublicKey)
                .build();
    }


    public void initialize() {
        mView.initializeTitle();
        mView.setIdentificationTypeListeners();
        mView.setIdentificationNumberListeners();
        mView.setNextButtonListeners();
        mView.setBackButtonListeners();
    }


    public void loadIdentificationTypes() {
        getIdentificationTypesAsync();
    }

    private void getIdentificationTypesAsync() {

        mMercadoPago.getIdentificationTypes(new Callback<List<IdentificationType>>() {
            @Override
            public void success(List<IdentificationType> identificationTypes) {
                if (identificationTypes.isEmpty()) {
                    mView.startErrorView(mContext.getString(R.string.mpsdk_standard_error_message),
                            "identification types call is empty at IdentificationActivity");
                } else {
                    if (mIdentificationType == null && mSavedIdentificationType == null) {
                        mIdentificationType = identificationTypes.get(0);
                    } else {
                        mIdentificationType = mSavedIdentificationType;
                    }
                    mView.initializeIdentificationTypes(identificationTypes);
                    mIdentificationTypes = identificationTypes;
                    mView.showInputContainer();
                }
            }

            @Override
            public void failure(ApiException apiException) {
                setFailureRecovery(new FailureRecovery() {
                    @Override
                    public void recover() {
                        getIdentificationTypesAsync();
                    }
                });
                mView.showApiExceptionError(apiException);
            }
        });
    }

    public boolean checkIsEmptyOrValidIdentificationNumber() {
        return TextUtils.isEmpty(mIdentificationNumber) || validateIdentificationNumber();
    }

    public void saveIdentificationNumber(String identificationNumber) {
        this.mIdentificationNumber = identificationNumber;
    }

    public void saveIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
        if (identificationType != null) {
            mIdentification.setType(identificationType.getId());
            mView.setIdentificationNumberRestrictions(identificationType.getType());
        }
    }

    public IdentificationType getIdentificationType() {
        return this.mIdentificationType;
    }

    public void setIdentificationNumber(String number) {
        mIdentificationNumber = number;
        mIdentification.setNumber(number);
    }

    public String getIdentificationNumber() {
        return mIdentificationNumber;
    }

    public Payer getPayer() {
        return mPayer;
    }

    public void setPayer(Payer payer) {
        this.mPayer = payer;
    }

    public int getIdentificationNumberMaxLength() {
        int maxLength = CARD_DEFAULT_IDENTIFICATION_NUMBER_LENGTH;
        if (mIdentificationType != null) {
            maxLength = mIdentificationType.getMaxLength();
        }
        return maxLength;
    }

    public boolean validateIdentificationNumber() {
        mIdentification.setNumber(getIdentificationNumber());
        boolean ans = mIdentificationType.validateIdentificationNumber(mIdentification);

        if (ans) {
            mView.clearErrorView();
            mView.clearErrorIdentificationNumber();
        } else {
            setCardIdentificationErrorView(mContext.getString(R.string.mpsdk_invalid_identification_number));
        }
        return ans;
    }

    //TODO refactor
    public boolean validatePayerName() {
        Boolean isValidName = true;

        if (isEmpty(mPayer.getName())) {
            isValidName = false;
        }

        return isValidName;
    }

    //TODO refactor
    public boolean validatePayerSurname() {
        Boolean isValidSurname = true;

        if (isEmpty(mPayer.getSurname())) {
            isValidSurname = false;
        }

        return isValidSurname;
    }

    private void setCardIdentificationErrorView(String message) {
        mView.setErrorView(message);
        mView.setErrorIdentificationNumber();
    }

    public void recoverFromFailure() {
        if (mFailureRecovery != null) {
            mFailureRecovery.recover();
        }
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
    }
}
