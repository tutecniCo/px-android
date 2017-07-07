package com.mercadopago.model;

import com.mercadopago.mocks.IdentificationTypes;
import com.mercadopago.mocks.Identifications;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Created by marlanti on 7/7/17.
 */

public class IdentificationTypeTest{

    @Test
    public void testIdentificationNumber() {

        IdentificationType type = IdentificationTypes.getIdentificationTypeMLA();
        Identification identification = Identifications.getIdentificationMLA();
        assertTrue(type.validateIdentificationNumber(identification));

    }

    @Test
    public void testIdentificationNumberWrongLength() {

        Identification identification = Identifications.getIdentificationMLA();
        identification.setNumber("123456");

        IdentificationType type = IdentificationTypes.getIdentificationTypeMLA();

        assertFalse(type.validateIdentificationNumber(identification));

        identification.setNumber("12345678901234567890");

        type = IdentificationTypes.getIdentificationTypeMLA();
        assertFalse(type.validateIdentificationNumber(identification));
    }

    @Test
    public void testIdentificationNumberWithNullIdentificationrValues() {

        IdentificationType type = IdentificationTypes.getIdentificationTypeMLA();

        assertFalse(type.validateIdentificationNumber(null));

        Identification identification = Identifications.getIdentificationMLA();

        identification.setNumber(null);

        assertFalse(type.validateIdentificationNumber(identification));

        identification = Identifications.getIdentificationMLA();

        identification.setType(null);

        assertFalse(type.validateIdentificationNumber(identification));

    }
    @Test
    public void testIdentificationNumberNullMinMaxLength() {

        IdentificationType type = IdentificationTypes.getIdentificationTypeMLA();
        type.setMinLength(null);

        Identification identification = Identifications.getIdentificationMLA();

        assertTrue(type.validateIdentificationNumber(identification));

        type = IdentificationTypes.getIdentificationTypeMLA();
        type.setMaxLength(null);

        assertTrue(type.validateIdentificationNumber(identification));
    }
}
