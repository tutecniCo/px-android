package com.mercadopago.model;

import com.mercadopago.mocks.Identifications;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marlanti on 7/7/17.
 */

public class IdentificationTest {

    @Test
    public void testIdentification() {

        Identification identification = Identifications.getIdentificationMLA();

        assertTrue(identification.validateIdentification());
    }

    @Test
    public void testIdentificationWithEmptyType() {

        Identification identification = new Identification();
        Identification mock = Identifications.getIdentificationMLA();

        identification.setNumber(mock.getNumber());
        boolean validate = identification.validateIdentification();
        assertFalse(validate);
    }

    @Test
    public void testIdentificationWithEmptyNumber() {

        Identification identification = new Identification();
        Identification mock = Identifications.getIdentificationMLA();

        identification.setType(mock.getType());

        assertFalse(identification.validateIdentification());
    }
}
