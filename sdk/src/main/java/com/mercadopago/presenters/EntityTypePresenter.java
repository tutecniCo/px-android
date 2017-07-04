package com.mercadopago.presenters;

/**
 * Created by marlanti on 3/3/17.
 */

import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.EntityType;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Site;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.providers.EntityTypeProvider;
import com.mercadopago.views.EntityTypeActivityView;

import java.util.List;


public class EntityTypePresenter extends MvpPresenter<EntityTypeActivityView, EntityTypeProvider> {

    private FailureRecovery mFailureRecovery;


    //Activity parameters
    private String mPublicKey;
    private PaymentMethod mPaymentMethod;
    private Identification mIdentification;
    private IdentificationType mIdentificationType;
    private Site mSite;

    //Fields
    private List<EntityType> mEntityTypes;

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.mPaymentMethod = paymentMethod;
    }

    public void setPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }


    private void setFailureRecovery(FailureRecovery failureRecovery) {
        this.mFailureRecovery = failureRecovery;
    }

    public boolean isCardInfoAvailable() {
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


    public void validate() throws IllegalStateException {
        if (mPaymentMethod == null) {
            throw new IllegalStateException("payment method is null");
        }
        if (mIdentification == null) {
            throw new IllegalStateException("Identification is not set");
        }
        if (mIdentificationType == null) {
            throw new IllegalStateException("Identification Type is not set");
        }
        if (mPublicKey == null) {
            throw new IllegalStateException("public key not set");
        }
        if (mSite == null) {
            throw new IllegalStateException("site not set");
        }
    }

    public void initialize() {
        try {
            validate();
            showEntityTypes();
        } catch (IllegalStateException exception) {
            String standardErrorMessage = getResourcesProvider().getStandardErrorMessageGotten();
            getView().showError(standardErrorMessage, exception.getMessage());
        }
    }

    private void showEntityTypes() {
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
        this.mEntityTypes = getResourcesProvider().getEntityTypesBySite(mSite);
        resolveEntityTypes(mEntityTypes);
    }

    protected void resolveEntityTypes(List<EntityType> entityTypes) {

        mEntityTypes = entityTypes;
        if (mEntityTypes == null || mEntityTypes.isEmpty()) {
            String standardErrorMessage = getResourcesProvider().getStandardErrorMessageGotten();
            String errorDetail = getResourcesProvider().getEmptyEntityTypesErrorMessage();
            getView().showError(standardErrorMessage, errorDetail);
        } else if (mEntityTypes.size() == 1) {
            getView().finishWithResult(entityTypes.get(0));
        } else {
            getView().initialize();
            getView().showTimer();
            getView().trackScreen();
            String title = getResourcesProvider().getEntityTypesTitle();
            getView().showHeader(title);
            getView().showEntityTypes(entityTypes, getDpadSelectionCallback());
        }
    }

    public void recoverFromFailure() {
        if (mFailureRecovery != null) {
            mFailureRecovery.recover();
        }
    }

    protected OnSelectedCallback<Integer> getDpadSelectionCallback() {
        return new OnSelectedCallback<Integer>() {
            @Override
            public void onSelected(Integer position) {
                onItemSelected(position);
            }
        };
    }

    public void onItemSelected(int position) {
        getView().finishWithResult(mEntityTypes.get(position));
    }

    public void setIdentification(Identification identification) {
        this.mIdentification = identification;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
    }

    public void setSite(Site mSite) {
        this.mSite = mSite;
    }

    public Site getSite() {
        return mSite;
    }

}
