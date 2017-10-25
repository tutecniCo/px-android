package com.mercadopago.preferences;

import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.constants.Sites;
import com.mercadopago.exceptions.CheckoutPreferenceException;
import com.mercadopago.model.Item;

import junit.framework.Assert;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.Assert.assertTrue;

/**
 * Created by mromar on 2/24/16.
 */
public class CheckoutPreferenceTest {

    @Test
    public void testWhenValidatePreferenceWithTwoItemsWithDifferentCurrencyIdReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithTwoItemsWithDifferentCurrencyId();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithTwoItemsIfOneHasCurrencyNullReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithTwoItemsOneHasCurrencyNull();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithTwoItemsWithIncorrectCurrencyReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithTwoItemsWithIncorrectCurrencyId();
        Assert.assertFalse(preference.itemsValid());
    }

    ///////////////////PAYMENTS_TYPES tests///////////////////
    public void testWhenValidatePreferenceWithAllPaymentsTypesExcludedReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithAllPaymentTypesExcluded();
        Assert.assertFalse(preference.validPaymentTypeExclusion());
    }

    @Test
    public void testWhenValidatePreferenceWithSomePaymentsTypesExcludedReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithSomePaymentTypesExcluded();
        assertTrue(preference.validPaymentTypeExclusion());
    }

    ///////////////////INSTALLMENTS tests///////////////////
    @Test
    public void testWhenValidatePreferenceWithPositiveDefaultInstallmentsNumberAndNegativeMaxInstallmentsNumberReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithPositiveDefaultInstallmentsNumberAndNegativeMaxInstallmentsNumber();
        Assert.assertFalse(preference.validInstallmentsPreference());
    }

    @Test
    public void testWhenValidatePreferenceWithPositiveMaxInstallmentsNumberAndNegativeDefaultInstallmentsNumberReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithPositiveMaxInstallmentsNumberAndNegativeDefaultInstallmentsNumber();
        Assert.assertFalse(preference.validInstallmentsPreference());
    }

    @Test
    public void testWhenValidatePreferenceWithMaxInstallmentsNumberPositiveReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithPositiveInstallmentsNumber();
        assertTrue(preference.validInstallmentsPreference());
    }

    @Test
    public void testWhenValidatePreferenceWithNegativeMaxInstallmentsNumberReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNegativeInstallmentsNumbers();
        Assert.assertFalse(preference.validInstallmentsPreference());
    }

    ///////////////////EXCEPTIONS tests///////////////////

    @Test
    public void testWhenValidatePreferenceValidNoThrowExceptionReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithOneItemValidActiveAndSomePaymentTypesExcluded();
        Boolean valid = true;

        try {
            preference.validate();
        } catch (CheckoutPreferenceException e) {
            valid = false;
        }
        finally{
            assertTrue(valid);
        }
    }

    @Test
    public void testWhenValidatePreferenceWithAllPaymentTypesExcludedThrowExceptionReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithOneItemValidActiveButAllPaymentTypesExcluded();

        try {
            preference.validate();
        } catch (CheckoutPreferenceException e) {
            assertTrue(e.getErrorCode() == CheckoutPreferenceException.EXCLUDED_ALL_PAYMENT_TYPES);
        }
    }

    @Test
    public void testWhenValidatePreferenceWithInstallmentsDefaultNumberAndInstallmentsNumberNegativeThrowExceptionReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithOneItemValidButInstallmenstsDefaultNumberAndInstallmentsNumberNegative();

        try {
            preference.validate();
        } catch (CheckoutPreferenceException e) {
            assertTrue(e.getErrorCode() == CheckoutPreferenceException.INVALID_INSTALLMENTS);
        }
    }

    @Test
    public void testWhenValidatePreferenceWithPreferenceExpiredThrowExceptionReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithOneItemValidButPreferenceExpired();

        try {
            preference.validate();
        } catch (CheckoutPreferenceException e) {
            assertTrue(e.getErrorCode() == CheckoutPreferenceException.EXPIRED_PREFERENCE);
        }
    }

    @Test (expected = IllegalStateException.class)
    public void testWhenValidatePreferenceWithNoItemsThrowException() {
        new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();
    }


    ///////////////////ITEMS tests///////////////////
    @Test
    public void testWhenValidatePreferenceWithTwoItemsWithoutUnitPriceReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithTwoItemsWithoutUnitPrice();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithNullUnitPriceItemReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNullUnitPriceItem();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithZeroItemQuantityReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithZeroItemQuantity();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithNegativeItemQuantityReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNegativeItemQuantity();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithNullItemQuantityReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNullItemQuantity();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWhenValidatePreferenceWithNullItemIdReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNullItemId();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test
    public void testWenValidatePreferenceWithNullItemsListReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNullItems();
        Assert.assertFalse(preference.itemsValid());
    }

    @Test (expected = IllegalStateException.class)
    public void testWhenValidatePreferenceWithEmptyItemsListThrowException() {
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItems(new ArrayList<Item>())
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();
        Assert.assertFalse(preference.itemsValid());
    }

    ///////////////////DATES tests///////////////////
    @Test
    public void testWhenPreferenceIsActiveReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getActivePreference();
        assertTrue(preference.isActive());
    }

    @Test
    public void testWhenPreferenceIsNotActiveReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getInactivePreference();
        Assert.assertFalse(preference.isActive());
    }

    @Test
    public void testWhenPreferenceIsNotExpiredReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getNotExpiredPreference();
        Assert.assertFalse(preference.isExpired());
    }

    @Test
    public void testWhenPreferenceIsExpiredReturnTrue() {
        com.mercadopago.preferences.CheckoutPreference preference = getExpiredPreference();

        assertTrue(preference.isExpired());
    }

    @Test
    public void testWhenValidatePreferenceWithNullExpirationDateToReturnFalse() {
        com.mercadopago.preferences.CheckoutPreference preference = getPreferenceWithNullExpirationDateTo();
        Assert.assertFalse(preference.isExpired());
    }

    ///////////////////Getters preferences with different DATES///////////////////
    private com.mercadopago.preferences.CheckoutPreference getActivePreference() {
        Date pastDate = new Date();
        pastDate.setTime((new Date().getTime()) - 1000 * 60 * 60);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setActiveFrom(pastDate)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getInactivePreference() {
        GregorianCalendar calendar = new GregorianCalendar(2100, 3, 3); //Date should be after that today
        Date date = calendar.getTime();
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setActiveFrom(date)
                .build();
        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getNotExpiredPreference() {
        GregorianCalendar calendar = new GregorianCalendar(2100, 7, 3); //Date should be after that today
        Date date = calendar.getTime();
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setActiveFrom(date)
                .build();
        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getExpiredPreference() {
        GregorianCalendar calendar = new GregorianCalendar(2015, 3, 3); //Date should be before that today
        Date date = calendar.getTime();
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setSite(Sites.ARGENTINA)
                .setExpirationDate(date)
                .build();
        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullExpirationDateFrom() {
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();
        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullExpirationDateTo() {
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setSite(Sites.ARGENTINA)
                .build();
        return preference;
    }

    ///////////////////Getters preferences with different CURRENCY///////////////////
    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItemsWithSameCurrencyId() {
        Item itemA = new Item("123", BigDecimal.TEN);
        Item itemB = new Item("456", BigDecimal.TEN);

        itemA.setCurrencyId("ARS");
        itemB.setCurrencyId("ARS");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItemsWithDifferentCurrencyId() {
        Item itemA = new Item("123", BigDecimal.TEN);
        Item itemB = new Item("456", BigDecimal.TEN);

        itemA.setCurrencyId("US$");
        itemB.setCurrencyId("ARS");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItemsOneHasCurrencyNull() {
        Item itemA = new Item("123", BigDecimal.ONE);
        Item itemB = new Item("123", BigDecimal.TEN);

        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItemsWithIncorrectCurrencyId() {
        Item itemA = new Item("123", BigDecimal.TEN);
        Item itemB = new Item("456", BigDecimal.TEN);

        itemA.setCurrencyId("USD");
        itemB.setCurrencyId("PesoARG");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    ///////////////////Getters preferences with different ITEMS///////////////////
    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItems() {
        Item itemA = new Item("123", BigDecimal.TEN);
        Item itemB = new Item("456", BigDecimal.TEN);

        itemA.setCurrencyId("USD");
        itemB.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithTwoItemsWithoutUnitPrice() {
        Item itemA = new Item("123", BigDecimal.TEN);
        Item itemB = new Item("456", BigDecimal.TEN);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addItem(itemA)
                .addItem(itemB)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNegativeUnitPriceItem() {
        Item item = new Item("123", BigDecimal.TEN);

        item.setUnitPrice(new BigDecimal(-1));

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullUnitPriceItem() {

        Item item = new Item("123", null);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithZeroItemQuantity() {
        Item item = new Item("123", BigDecimal.ZERO);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNegativeItemQuantity() {
        Item item = new Item("123", BigDecimal.ONE.negate());

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullItemQuantity() {
        Item item = new Item("123", null);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullItemId() {
        Item item = new Item("123", BigDecimal.ONE.negate());

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNullItems() {
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(new Item("item", 1, BigDecimal.ONE))
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        preference.setItems(null);
        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithEmptyItems() {
        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();
        return preference;
    }

    ///////////////////Getters preferences with different PAYMENTS_TYPES///////////////////
    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithAllPaymentTypesExcluded() {

        ArrayList<String> paymentTypes= new ArrayList<>();
        Item item = new Item("123", BigDecimal.ONE);

        paymentTypes.addAll(PaymentTypes.getAllPaymentTypes());

        item.setUnitPrice(new BigDecimal(2));

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(item)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addExcludedPaymentTypes(paymentTypes)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithSomePaymentTypesExcluded() {

        Item itemA = new Item("123", BigDecimal.ONE);
        itemA.setCurrencyId("USD");


        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addExcludedPaymentType(PaymentTypes.CREDIT_CARD)
                .addExcludedPaymentType(PaymentTypes.DEBIT_CARD)
                .addExcludedPaymentType(PaymentTypes.PREPAID_CARD)
                .build();

        return preference;
    }

    ///////////////////Getters preferences with different INSTALLMENT///////////////////
    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithPositiveMaxInstallmentsNumberAndNegativeDefaultInstallmentsNumber() {
        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setMaxInstallments(1)
                .setDefaultInstallments(-3)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithPositiveDefaultInstallmentsNumberAndNegativeMaxInstallmentsNumber() {

        Item itemA = new Item("123", BigDecimal.ONE);
        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("email@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setMaxInstallments(-1)
                .setDefaultInstallments(3)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithPositiveInstallmentsNumber() {

        Item itemA = new Item("123", BigDecimal.ONE);
        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setMaxInstallments(1)
                .setDefaultInstallments(3)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithNegativeInstallmentsNumbers() {
        Item itemA = new Item("123", BigDecimal.ONE);
        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setMaxInstallments(-1)
                .setDefaultInstallments(-1)
                .build();

        return preference;
    }

    ///////////////////Getters preferences with different EXCEPTIONS///////////////////
    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithOneItemValidActiveAndSomePaymentTypesExcluded() {

        ArrayList<String> paymentTypes= new ArrayList<>();
        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        Date pastDate = new Date();
        pastDate.setTime((new Date().getTime()) - 1000 * 60 * 60);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addExcludedPaymentType(PaymentTypes.CREDIT_CARD)
                .addExcludedPaymentType(PaymentTypes.DEBIT_CARD)
                .addExcludedPaymentType(PaymentTypes.PREPAID_CARD)
                .setActiveFrom(pastDate)
                .setMaxInstallments(1)
                .setDefaultInstallments(1)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithOneItemValidActiveButAllPaymentTypesExcluded() {

        ArrayList<String> paymentTypes= new ArrayList<>();
        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        Date pastDate = new Date();
        pastDate.setTime((new Date().getTime()) - 1000 * 60 * 60);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .addExcludedPaymentTypes(PaymentTypes.getAllPaymentTypes())
                .setActiveFrom(pastDate)
                .build();

        return preference;
    }


    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithOneItemValidButInstallmenstsDefaultNumberAndInstallmentsNumberNegative() {

        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        Date pastDate = new Date();
        pastDate.setTime((new Date().getTime()) - 1000 * 60 * 60);

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setMaxInstallments(-1)
                .setDefaultInstallments(-3)
                .setActiveFrom(pastDate)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithOneItemValidButPreferenceExpired() {
        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        GregorianCalendar calendar = new GregorianCalendar(2015, 3, 3); //Date should be before that today
        Date date = calendar.getTime();

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .setExpirationDate(date)
                .build();

        return preference;
    }

    private com.mercadopago.preferences.CheckoutPreference getPreferenceWithOneItemValidButPreferenceInactive() {

        Item itemA = new Item("123", BigDecimal.ONE);

        itemA.setUnitPrice(new BigDecimal(2));
        itemA.setCurrencyId("USD");

        com.mercadopago.preferences.CheckoutPreference preference = new com.mercadopago.preferences.CheckoutPreference.Builder()
                .addItem(itemA)
                .setPayerEmail("test@gmail.com")
                .setSite(Sites.ARGENTINA)
                .build();

        return preference;
    }
}