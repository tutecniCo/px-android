package com.mercadopago.model;

import com.mercadopago.CheckoutActivity;
import com.mercadopago.R;
import com.mercadopago.exceptions.CardTokenException;
import com.mercadopago.exceptions.ExceptionHandler;
import com.mercadopago.test.BaseTest;
import com.mercadopago.test.StaticMock;

public class CardTokenTest extends BaseTest<CheckoutActivity> {

    public CardTokenTest() {
        super(CheckoutActivity.class);
    }

    public void testConstructor() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        assertTrue(cardToken.getCardNumber().equals(StaticMock.DUMMY_CARD_NUMBER));
        assertTrue(cardToken.getExpirationMonth() == StaticMock.DUMMY_EXPIRATION_MONTH);
        assertTrue(cardToken.getExpirationYear() == StaticMock.DUMMY_EXPIRATION_YEAR_LONG);
        assertTrue(cardToken.getSecurityCode().equals(StaticMock.DUMMY_SECURITY_CODE));
        assertTrue(cardToken.getCardholder().getName().equals(StaticMock.DUMMY_CARDHOLDER_NAME));
        assertTrue(cardToken.getCardholder().getIdentification().getType().equals(StaticMock.DUMMY_IDENTIFICATION_TYPE));
        assertTrue(cardToken.getCardholder().getIdentification().getNumber().equals(StaticMock.DUMMY_IDENTIFICATION_NUMBER));
    }
    
    public void testValidate() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validate(true));
    }

    public void testValidateNoSecurityCode() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validate(false));
    }

    // * Card number
    public void testCardNumber() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateCardNumber());
    }

    public void testCardNumberEmpty() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("");

        assertTrue(!cardToken.validateCardNumber());
    }

    public void testCardNumberMinLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("4444");

        assertTrue(!cardToken.validateCardNumber());
    }

    public void testCardNumberMaxLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("44440000444400004444");

        assertTrue(!cardToken.validateCardNumber());
    }

    public void testCardNumberWithPaymentMethod() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateCardNumber(paymentMethod);
        } catch (CardTokenException ex) {
            fail("Failed on validate card number with payment.json method:" + ex.getMessage());
        }
    }

    public void testCardNumberWithPaymentMethodEmptyCardNumber() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("");
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateCardNumber(paymentMethod);
            fail("Should have failed on empty card number");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_EMPTY_CARD);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_empty_card);
            assertEquals(message, expectedMessage);
        }
    }

    public void testCardNumberWithPaymentMethodInvalidBin() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("5300888800009999");
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateCardNumber(paymentMethod);
            fail("Should have failed on invalid bin");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_CARD_BIN);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_card_bin);
            assertEquals(message, expectedMessage);
        }
    }

    public void testCardNumberWithPaymentMethodInvalidLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("466057001125");
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateCardNumber(paymentMethod);
            fail("Should have failed on invalid card length");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_CARD_LENGTH);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_card_length, String.valueOf(16));
            assertEquals(message, expectedMessage);
        }
    }

    public void testCardNumberWithPaymentMethodInvalidLuhn() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("4660888888888888");
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateCardNumber(paymentMethod);
            fail("Should have failed on invalid luhn");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_CARD_LUHN);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_card_luhn);
            assertEquals(message, expectedMessage);
        }
    }

    // * Security code
    public void testSecurityCode() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateSecurityCode());
    }

    public void testSecurityCodeEmpty() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("");

        assertTrue(!cardToken.validateSecurityCode());
    }

    public void testSecurityCodeMinLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("4");

        assertTrue(!cardToken.validateSecurityCode());
    }
    
    public void testSecurityCodeMaxLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("44444");

        assertTrue(!cardToken.validateSecurityCode());
    }

    public void testSecurityCodeWithPaymentMethod() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateSecurityCode(paymentMethod);
        } catch (CardTokenException ex) {
            fail("Failed on validate security code with payment.json method:" + ex.getMessage());
        }
    }

    public void testSecurityCodeWithPaymentMethodInvalidBin() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("5300888800009999");
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateSecurityCode(paymentMethod);
            fail("Should have failed on invalid bin");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_FIELD);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_field);
            assertEquals(message, expectedMessage);
        }
    }

    public void testSecurityCodeWithPaymentMethodInvalidLength() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("4444");
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(getApplicationContext());

        try {
            cardToken.validateSecurityCode(paymentMethod);
            fail("Should have failed on invalid security code length");
        } catch (CardTokenException ex) {
            assertEquals(ex.getErrorCode(), CardTokenException.INVALID_CVV_LENGTH);
            String message = ExceptionHandler.getErrorMessage(getApplicationContext(), ex);
            String expectedMessage = getApplicationContext().getString(R.string.mpsdk_invalid_cvv_length, String.valueOf(3));
            assertEquals(message, expectedMessage);
//            assertTrue(ex.getMessage().equals(getApplicationContext().getString(R.string.mpsdk_invalid_cvv_length, String.valueOf(3))));
        }
    }

    // TODO: test cvv not required

    // * Expiry date

    
    public void testExpiryDate() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateShortYear() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationYear(18);

        assertTrue(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateNullMonth() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationMonth(null);

        assertFalse(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateWrongMonth() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationMonth(13);

        assertFalse(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateNullYear() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationYear(null);

        assertFalse(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateWrongYear() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationYear(2000);

        assertFalse(cardToken.validateExpiryDate());
    }

    
    public void testExpiryDateWrongShortYear() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setExpirationYear(10);

        assertFalse(cardToken.validateExpiryDate());
    }

    // * Identification

    
    public void testIdentification() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateIdentification());
    }

    
    public void testIdentificationNullCardholder() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardholder(null);

        assertFalse(cardToken.validateIdentification());
    }

    
    public void testIdentificationNullIdentification() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().setIdentification(null);

        assertFalse(cardToken.validateIdentification());
    }

    
    public void testIdentificationEmptyType() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().getIdentification().setType("");

        assertFalse(cardToken.validateIdentification());
    }

    
    public void testIdentificationEmptyNumber() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().getIdentification().setNumber("");

        assertFalse(cardToken.validateIdentification());
    }

    
    public void testIdentificationNumber() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        com.mercadopago.model.IdentificationType type = StaticMock.getIdentificationType();

        assertTrue(cardToken.validateIdentificationNumber(type));
    }

    
    public void testIdentificationNumberWrongLength() {

        com.mercadopago.model.CardToken cardToken;
        com.mercadopago.model.IdentificationType type;

        cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().getIdentification().setNumber("123456");
        type = StaticMock.getIdentificationType();
        assertFalse(cardToken.validateIdentificationNumber(type));

        cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().getIdentification().setNumber("12345678901234567890");
        type = StaticMock.getIdentificationType();
        assertFalse(cardToken.validateIdentificationNumber(type));
    }

    
    public void testIdentificationNumberNullIdType() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateIdentificationNumber(null));
    }

    
    public void testIdentificationNumberNullCardholderValues() {

        com.mercadopago.model.CardToken cardToken;
        com.mercadopago.model.IdentificationType type;

        cardToken = StaticMock.getCardToken();
        cardToken.setCardholder(null);
        type = StaticMock.getIdentificationType();
        assertFalse(cardToken.validateIdentificationNumber(type));

        cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().setIdentification(null);
        type = StaticMock.getIdentificationType();
        assertFalse(cardToken.validateIdentificationNumber(type));

        cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().getIdentification().setNumber(null);
        type = StaticMock.getIdentificationType();
        assertFalse(cardToken.validateIdentificationNumber(type));
    }

    
    public void testIdentificationNumberNullMinMaxLength() {

        com.mercadopago.model.CardToken cardToken;
        IdentificationType type;

        cardToken = StaticMock.getCardToken();
        type = StaticMock.getIdentificationType();
        type.setMinLength(null);
        assertTrue(cardToken.validateIdentificationNumber(type));

        cardToken = StaticMock.getCardToken();
        type = StaticMock.getIdentificationType();
        type.setMaxLength(null);
        assertTrue(cardToken.validateIdentificationNumber(type));
    }

    // * Cardholder name

    
    public void testCardholderName() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.validateCardholderName());
    }

    
    public void testCardholderNameEmpty() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().setName("");

        assertFalse(cardToken.validateCardholderName());
    }

    
    public void testCardholderNameNull() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.getCardholder().setName(null);

        assertFalse(cardToken.validateCardholderName());
    }

    
    public void testCardholderNameCardholderNull() {

        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardholder(null);

        assertFalse(cardToken.validateCardholderName());
    }

    // * Luhn

    
    public void testLuhn() {

        assertTrue(com.mercadopago.model.CardToken.checkLuhn(StaticMock.DUMMY_CARD_NUMBER));
    }

    
    public void testLuhnNullOrEmptyCardNumber() {

        assertFalse(com.mercadopago.model.CardToken.checkLuhn(null));
        assertFalse(com.mercadopago.model.CardToken.checkLuhn(""));
    }

    
    public void testLuhnWrongCardNumber() {

        assertFalse(com.mercadopago.model.CardToken.checkLuhn("1111000000000000"));
    }
}
