package com.mercadopago.preferences;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mromar on 10/23/17.
 */

public class PaymentPreference {

    @SerializedName("installments")
    private Integer maxAcceptedInstallments;
    private Integer defaultInstallments;
    private List<String> excludedPaymentMethodIds;
    private List<String> excludedPaymentTypeIds;
    private String defaultPaymentMethodId;
    private String defaultPaymentTypeId;

    public Integer getMaxAcceptedInstallments() {
        return maxAcceptedInstallments;
    }

    public void setMaxAcceptedInstallments(Integer maxAcceptedInstallments) {
        this.maxAcceptedInstallments = maxAcceptedInstallments;
    }

    public Integer getDefaultInstallments() {
        return defaultInstallments;
    }

    public void setDefaultInstallments(Integer defaultInstallments) {
        this.defaultInstallments = defaultInstallments;
    }

    public List<String> getExcludedPaymentMethodIds() {
        return excludedPaymentMethodIds;
    }

    public void setExcludedPaymentMethodIds(List<String> excludedPaymentMethodIds) {
        this.excludedPaymentMethodIds = excludedPaymentMethodIds;
    }

    public List<String> getExcludedPaymentTypeIds() {
        return excludedPaymentTypeIds;
    }

    public void setExcludedPaymentTypeIds(List<String> excludedPaymentTypeIds) {
        this.excludedPaymentTypeIds = excludedPaymentTypeIds;
    }

    public String getDefaultPaymentMethodId() {
        return defaultPaymentMethodId;
    }

    public void setDefaultPaymentMethodId(String defaultPaymentMethodId) {
        this.defaultPaymentMethodId = defaultPaymentMethodId;
    }

    public String getDefaultPaymentTypeId() {
        return defaultPaymentTypeId;
    }

    public void setDefaultPaymentTypeId(String defaultPaymentTypeId) {
        this.defaultPaymentTypeId = defaultPaymentTypeId;
    }
}
