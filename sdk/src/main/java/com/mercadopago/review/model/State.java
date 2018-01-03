package com.mercadopago.review.model;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.model.Discount;
import com.mercadopago.model.Issuer;
import com.mercadopago.model.Item;
import com.mercadopago.model.PayerCost;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Site;
import com.mercadopago.model.Token;
import com.mercadopago.util.JsonUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by nfortuna on 1/3/18.
 */

public class State {

    public final String publicKey;
    public final Site site;
    public final Issuer issuer;
    public final boolean termsAndConditionsEnabled;
    public final boolean editionEnabled;
    public final boolean discountEnabled;
    public final BigDecimal amount;
    public final Discount discount;
    public final PayerCost payerCost;
    public final Token token;
    public final PaymentMethod paymentMethod;
    public final String paymentMethodCommentInfo;
    public final String paymentMethodDescriptionInfo;
    public final List<Item> items;


//    public State(@NonNull final Builder builder) {
//
//    }
//
//    public class Builder {
//        public String publicKey;
//        public Site site;
//        public Issuer issuer;
//        public boolean termsAndConditionsEnabled;
//        public boolean editionEnabled;
//        public boolean discountEnabled;
//        public BigDecimal amount;
//        public Discount discount;
//        public PayerCost payerCost;
//        public Token token;
//        public PaymentMethod paymentMethod;
//        public String paymentMethodCommentInfo;
//        public String paymentMethodDescriptionInfo;
//        public List<Item> items;
//
//        public void setPublicKey(String publicKey) {
//            this.publicKey = publicKey;
//        }
//    }

//    mPresenter.setItems(items);
//        mPresenter.setAmount(amount);
//        mPresenter.setSite(mSite);
//        mPresenter.setIssuer(mIssuer);
//        mPresenter.setDiscount(discount);
//        mPresenter.setPayerCost(payerCost);
//        mPresenter.setToken(token);
//        mPresenter.setPaymentMethod(paymentMethod);
//        mPresenter.setPaymentMethodCommentInfo(paymentMethodCommentInfo);
//        mPresenter.setPaymentMethodDescriptionInfo(paymentMethodDescriptionInfo);
//        mPresenter.setEditionEnabled(editionEnabled);
//        mPresenter.setDecorationPreference(mDecorationPreference);
//        mPresenter.setTermsAndConditionsEnabled(termsAndConditionsEnabled);
//        mPresenter.setDiscountEnabled(discountEnabled);
//
//        if (mReviewScreenPreference == null || !mReviewScreenPreference.hasReviewOrder()) {
//        mPresenter.setReviewOrder(getDefaultOrder());
//    } else {
//        mPresenter.setReviewOrder(mReviewScreenPreference.getReviewOrder());
//    }

}
