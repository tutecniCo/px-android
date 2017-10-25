package com.mercadopago.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PayerCostTest {

    @Test
    public void substringTEARateWhenGetTea() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithLabels();

        String tea = "69,73%";
        assertTrue(payerCost.getTEAPercent().equals(tea));
    }

    @Test
    public void substringCFTRateWhenGetCft() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithLabels();

        String cft = "88,33%";
        assertTrue(payerCost.getCFTPercent().equals(cft));
    }

    @Test
    public void hasRatesTrueWhenPayerCostHasLabelRates() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithLabels();

        assertTrue(payerCost.hasRates());
    }

    @Test
    public void hasRatesFalseWhenPayerCostHasNotLabelRates() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithoutLabels();

        assertFalse(payerCost.hasRates());
    }

    @Test
    public void hasTEATrueWhenPayerCostHasTEALabel() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithLabels();

        assertTrue(payerCost.hasTEA());
    }

    @Test
    public void hasCFTTrueWhenPayerCostHasCFTLabel() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithLabels();

        assertTrue(payerCost.hasCFT());
    }

    @Test
    public void hasTEAFalseWhenPayerCostHasNotTEALabel() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithoutLabels();

        assertFalse(payerCost.hasTEA());
    }

    @Test
    public void hasCFTFalseWhenPayerCostHasNotCFTLabel() {
        com.mercadopago.model.PayerCost payerCost = getPayerCostWithoutLabels();

        assertFalse(payerCost.hasCFT());
    }

    private com.mercadopago.model.PayerCost getPayerCostWithLabels() {
        String label = "CFT_88,33%|TEA_69,73%";
        com.mercadopago.model.PayerCost payerCost = new com.mercadopago.model.PayerCost();
        List<String> labels = new ArrayList<String>();

        payerCost.setInstallments(3);
        payerCost.setInstallmentRate(new BigDecimal(10.97));

        labels.add(label);
        payerCost.setLabels(labels);

        return payerCost;
    }

    private com.mercadopago.model.PayerCost getPayerCostWithoutLabels() {
        com.mercadopago.model.PayerCost payerCost = new com.mercadopago.model.PayerCost();

        payerCost.setInstallments(3);
        payerCost.setInstallmentRate(new BigDecimal(10.97));

        return payerCost;
    }
}
