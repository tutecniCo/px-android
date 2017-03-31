package com.mercadopago.presenters;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.Discount;
import com.mercadopago.model.DiscountSearch;
import com.mercadopago.model.DiscountSearchItem;
import com.mercadopago.model.DiscountCard;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.providers.DiscountsProvider;

import java.math.BigDecimal;
import java.util.List;

import com.mercadopago.views.DiscountsActivityView;

/**
 * Created by mromar on 11/29/16.
 */

public class DiscountsPresenter extends MvpPresenter<DiscountsActivityView, DiscountsProvider> {

    private DiscountsActivityView mDiscountsView;

    //Activity parameters
    private String mPublicKey;
    private String mPayerEmail;
    private BigDecimal mTransactionAmount;
    private Discount mDiscount;
    private Boolean mDirectDiscountEnabled;

    private DiscountSearch mDiscountSearch;

    public void initialize() {
        getDiscountSearch();
    }

    private void initDiscountFlow() {
        if (mDirectDiscountEnabled && isTransactionAmountValid()) {
            getView().hideDiscountSummary();
            getDirectDiscount();
        } else {
            getView().requestDiscountCode();
        }
    }

    private Boolean isTransactionAmountValid() {
        return mTransactionAmount != null && mTransactionAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    private void getDiscountSearch() {
        getView().showProgress();

        getResourcesProvider().getDiscountSearch(mTransactionAmount.toString(), mPayerEmail,new OnResourcesRetrievedCallback<DiscountSearch>() {
            @Override
            public void onSuccess(DiscountSearch discountSearch) {
                mDiscountSearch = discountSearch;
                resolveDiscountSearch();
            }

            @Override
            public void onFailure(MercadoPagoError error) {
                getView().finishWithCancelResult();
            }
        });
    }

    private void resolveDiscountSearch() {
        if (viewAttached()) {

            if (noDiscountsAvailable()) {
                getView().finishWithCancelResult();
            } else {
                showAvailableOptions();
                getView().hideProgress();
            }
        }
    }

    private void showAvailableOptions() {
        if (searchItemsAvailable()) {
            getView().showSearchItems(mDiscountSearch.getGroups(), getDiscountSearchItemSelectionCallback());
        }
    }

    private OnSelectedCallback<DiscountSearchItem> getDiscountSearchItemSelectionCallback() {
        return new OnSelectedCallback<DiscountSearchItem>() {
            @Override
            public void onSelected(DiscountSearchItem item) {
                selectItem(item);
            }
        };
    }

    private void selectItem(DiscountSearchItem item) {
        if (item.isDiscountType()) {
            resolveDiscountType(item);
        } else if (item.isDiscountCardType()) {
            resolveDiscountCardType(item);
        }
    }

    private void resolveDiscountType(DiscountSearchItem item) {
        List<Discount> discounts = mDiscountSearch.getDiscounts();
        List<DiscountCard> discountCards = mDiscountSearch.getDiscountsCards();

        //TODO mejorar, delegar en métodos
        for (Discount discount : discounts) {
            if (discount.getId().equals(item.getId())) {
                mDiscount = discount;
            }
        }

        for (DiscountCard discountCard : discountCards) {
            if (discountCard.getDiscount().getId().equals(item.getId())) {
                mDiscount = discountCard.getDiscount();
            }
        }

        getView().hideDiscountSearchSelection();

        if (mDiscount != null && isCouponDiscount()) {
            getView().requestDiscountCode();
        } else {
            getView().drawSummary();
        }
    }

    private Boolean isCouponDiscount() {
        return !isEmpty(mDiscount.getCouponCode());
    }

    private void resolveDiscountCardType(DiscountSearchItem item) {
        List<DiscountCard> discountsCards = mDiscountSearch.getDiscountsCards();

        for (DiscountCard discountCard : discountsCards) {
            if (discountCard.getDiscount().getId().equals(item.getId())) {
                mDiscount = discountCard.getDiscount();
            }
        }

        getView().hideDiscountSearchSelection();
        getView().drawSummary();
    }

    private boolean searchItemsAvailable() {
        return mDiscountSearch != null && mDiscountSearch.getGroups() != null && !mDiscountSearch.getGroups().isEmpty();
    }

    private boolean noDiscountsAvailable() {
        return mDiscountSearch.getGroups() == null || mDiscountSearch.getGroups().isEmpty();
    }

    private boolean viewAttached() {
        return getView() != null;
    }

    private void getDirectDiscount() {
        getResourcesProvider().getDirectDiscount(mTransactionAmount.toString(), mPayerEmail, new OnResourcesRetrievedCallback<Discount>() {
            @Override
            public void onSuccess(Discount discount) {
                mDiscount = discount;
                mDiscountsView.drawSummary();
            }

            @Override
            public void onFailure(MercadoPagoError error) {
                mDiscountsView.requestDiscountCode();
            }
        });
    }

    private void getCodeDiscount(final String discountCode) {
        if (mDiscount != null && mDiscount.getCouponCode().equals(discountCode)) {
            getView().setSoftInputModeSummary();
            getView().hideKeyboard();
            getView().drawSummary();
        } else {
            getView().showCodeInputError(getResourcesProvider().getInvalidDiscountCodeErrorMessage());
        }
    }

    public void validateDiscountCodeInput(String discountCode) {
        if (isTransactionAmountValid()) {
            if (isEmpty(discountCode)) {
                getView().showEmptyDiscountCodeError();
            } else {
                getCodeDiscount(discountCode);
            }
        } else {
            getView().finishWithCancelResult();
        }
    }

    public Discount getDiscount() {
        return this.mDiscount;
    }

    public void setMerchantPublicKey(String publicKey) {
        this.mPublicKey = publicKey;
    }

    public void setPayerEmail(String payerEmail) {
        this.mPayerEmail = payerEmail;
    }

    public void setDiscount(Discount discount) {
        this.mDiscount = discount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.mTransactionAmount = transactionAmount;
    }

    public void setDirectDiscountEnabled(Boolean directDiscountEnabled) {
        this.mDirectDiscountEnabled = directDiscountEnabled;
    }

    public Boolean getDirectDiscountEnabled() {
        return this.mDirectDiscountEnabled;
    }

    public String getCurrencyId() {
        return mDiscount.getCurrencyId();
    }

    public BigDecimal getTransactionAmount() {
        return mTransactionAmount;
    }

    public BigDecimal getCouponAmount() {
        return mDiscount.getCouponAmount();
    }

    public String getPublicKey() {
        return this.mPublicKey;
    }

    private boolean isEmpty(String discountCode) {
        return discountCode == null || discountCode.isEmpty();
    }
}
