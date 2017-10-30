import android.support.test.InstrumentationRegistry;

import com.mercadopago.lite.model.CardToken;
import com.mercadopago.lite.model.PaymentMethod;

import com.mercadopago.lite.test.StaticMock;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class CardTokenTest {

    @Test
    public void testConstructor() {
        CardToken cardToken = StaticMock.getCardToken();

        assertTrue(cardToken.getCardNumber().equals(StaticMock.DUMMY_CARD_NUMBER));
        assertTrue(cardToken.getExpirationMonth() == StaticMock.DUMMY_EXPIRATION_MONTH);
        assertTrue(cardToken.getExpirationYear() == StaticMock.DUMMY_EXPIRATION_YEAR_LONG);
        assertTrue(cardToken.getSecurityCode().equals(StaticMock.DUMMY_SECURITY_CODE));
        assertTrue(cardToken.getCardholder().getName().equals(StaticMock.DUMMY_CARDHOLDER_NAME));
        assertTrue(cardToken.getCardholder().getIdentification().getType().equals(StaticMock.DUMMY_IDENTIFICATION_TYPE));
        assertTrue(cardToken.getCardholder().getIdentification().getNumber().equals(StaticMock.DUMMY_IDENTIFICATION_NUMBER));
    }

    @Test
    public void testValidateNoSecurityCode() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(cardToken.validateSecurityCode(paymentMethod));
    }

    // * Card number
    @Test
    public void testCardNumber() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberEmpty() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(!cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberMinLength() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("4444");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(!cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberMaxLength() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("44440000444400004444");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(!cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberWithPaymentMethodInvalidBin() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("5300888800009999");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertFalse(cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberWithPaymentMethodInvalidLength() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("466057001125");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertFalse(cardToken.validateCardNumber(paymentMethod));
    }

    @Test
    public void testCardNumberWithPaymentMethodInvalidLuhn() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("4660888888888888");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertFalse(cardToken.validateCardNumber(paymentMethod));
    }

    // * Security code
    @Test
    public void testSecurityCode() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeEmpty() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        cardToken.setSecurityCode("");

        assertTrue(!cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeMinLength() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        cardToken.setSecurityCode("4");

        assertTrue(!cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeMaxLength() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("44444");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(!cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeWithPaymentMethod() {
        CardToken cardToken = StaticMock.getCardToken();
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertTrue(cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeWithPaymentMethodInvalidBin() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setCardNumber("5300888800009999");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod( InstrumentationRegistry.getContext());

        assertFalse(cardToken.validateSecurityCode(paymentMethod));
    }

    @Test
    public void testSecurityCodeWithPaymentMethodInvalidLength() {
        CardToken cardToken = StaticMock.getCardToken();
        cardToken.setSecurityCode("4444");

        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());

        assertFalse(cardToken.validateSecurityCode(paymentMethod));
    }

    // TODO: test cvv not required

    // * Expiry date
    @Test
    public void testExpiryDate() {
        Integer month = 3;
        Integer year = 2029;

        assertTrue(CardToken.validateExpiryDate(month,year));
    }

    @Test
    public void testExpiryDateNullMonth() {
        Integer month = null;
        Integer year = 2020;

        assertFalse(CardToken.validateExpiryDate(month,year));
    }

    @Test
    public void testExpiryDateWrongMonth() {
        Integer month = 13;
        Integer year = 2020;

        assertFalse(CardToken.validateExpiryDate(month, year));
    }

    @Test
    public void testExpiryDateNullYear() {
        Integer month = 13;
        Integer year = null;

        assertFalse(CardToken.validateExpiryDate(month, year));
    }


//    public void testExpiryDateWrongYear() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.setExpirationYear(2000);
//
//        assertFalse(cardToken.validateExpiryDate());
//    }
//
//
//    public void testExpiryDateWrongShortYear() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.setExpirationYear(10);
//
//        assertFalse(cardToken.validateExpiryDate());
//    }
//
//    // * Identification
//
//
//    public void testIdentification() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//
//        assertTrue(cardToken.validateIdentification());
//    }
//
//
//    public void testIdentificationNullCardholder() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.setCardholder(null);
//
//        assertFalse(cardToken.validateIdentification());
//    }
//
//
//    public void testIdentificationNullIdentification() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().setIdentification(null);
//
//        assertFalse(cardToken.validateIdentification());
//    }
//
//
//    public void testIdentificationEmptyType() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().getIdentification().setType("");
//
//        assertFalse(cardToken.validateIdentification());
//    }
//
//
//    public void testIdentificationEmptyNumber() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().getIdentification().setNumber("");
//
//        assertFalse(cardToken.validateIdentification());
//    }
//
//
//    public void testIdentificationNumber() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//
//        com.mercadopago.model.IdentificationType type = StaticMock.getIdentificationType();
//
//        assertTrue(cardToken.validateIdentificationNumber(type));
//    }
//
//
//    public void testIdentificationNumberWrongLength() {
//
//        com.mercadopago.model.CardToken cardToken;
//        com.mercadopago.model.IdentificationType type;
//
//        cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().getIdentification().setNumber("123456");
//        type = StaticMock.getIdentificationType();
//        assertFalse(cardToken.validateIdentificationNumber(type));
//
//        cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().getIdentification().setNumber("12345678901234567890");
//        type = StaticMock.getIdentificationType();
//        assertFalse(cardToken.validateIdentificationNumber(type));
//    }
//
//
//    public void testIdentificationNumberNullIdType() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//
//        assertTrue(cardToken.validateIdentificationNumber(null));
//    }
//
//
//    public void testIdentificationNumberNullCardholderValues() {
//
//        com.mercadopago.model.CardToken cardToken;
//        com.mercadopago.model.IdentificationType type;
//
//        cardToken = StaticMock.getCardToken();
//        cardToken.setCardholder(null);
//        type = StaticMock.getIdentificationType();
//        assertFalse(cardToken.validateIdentificationNumber(type));
//
//        cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().setIdentification(null);
//        type = StaticMock.getIdentificationType();
//        assertFalse(cardToken.validateIdentificationNumber(type));
//
//        cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().getIdentification().setNumber(null);
//        type = StaticMock.getIdentificationType();
//        assertFalse(cardToken.validateIdentificationNumber(type));
//    }
//
//
//    public void testIdentificationNumberNullMinMaxLength() {
//
//        com.mercadopago.model.CardToken cardToken;
//        IdentificationType type;
//
//        cardToken = StaticMock.getCardToken();
//        type = StaticMock.getIdentificationType();
//        type.setMinLength(null);
//        assertTrue(cardToken.validateIdentificationNumber(type));
//
//        cardToken = StaticMock.getCardToken();
//        type = StaticMock.getIdentificationType();
//        type.setMaxLength(null);
//        assertTrue(cardToken.validateIdentificationNumber(type));
//    }
//
//    // * Cardholder name
//
//
//    public void testCardholderName() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//
//        assertTrue(cardToken.validateCardholderName());
//    }
//
//
//    public void testCardholderNameEmpty() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().setName("");
//
//        assertFalse(cardToken.validateCardholderName());
//    }
//
//
//    public void testCardholderNameNull() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.getCardholder().setName(null);
//
//        assertFalse(cardToken.validateCardholderName());
//    }
//
//
//    public void testCardholderNameCardholderNull() {
//
//        com.mercadopago.model.CardToken cardToken = StaticMock.getCardToken();
//        cardToken.setCardholder(null);
//
//        assertFalse(cardToken.validateCardholderName());
//    }
//
//    // * Luhn
//
//
//    public void testLuhn() {
//
//        assertTrue(com.mercadopago.model.CardToken.checkLuhn(StaticMock.DUMMY_CARD_NUMBER));
//    }
//
//
//    public void testLuhnNullOrEmptyCardNumber() {
//
//        assertFalse(com.mercadopago.model.CardToken.checkLuhn(null));
//        assertFalse(com.mercadopago.model.CardToken.checkLuhn(""));
//    }
//
//
//    public void testLuhnWrongCardNumber() {
//
//        assertFalse(com.mercadopago.model.CardToken.checkLuhn("1111000000000000"));
//    }
}
