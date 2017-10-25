package com.mercadopago.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ApiExceptionTest {

    @Test
    public void whenCauseIsNullThenContainsCauseShouldBeFalse() {
        com.mercadopago.model.ApiException apiException = new com.mercadopago.model.ApiException();
        assertFalse(apiException.containsCause(null));
    }

    @Test
    public void whenCauseIsEmptyThenContainsCauseShouldBeFalse() {
        com.mercadopago.model.ApiException apiException = new com.mercadopago.model.ApiException();
        apiException.setCause(new ArrayList<com.mercadopago.model.Cause>());
        assertFalse(apiException.containsCause("Some cause"));
    }

    @Test
    public void whenCauseIsNotInListOfCausesThenContainsCauseShouldBeFalse() {

        final com.mercadopago.model.Cause cause1 = new com.mercadopago.model.Cause();
        cause1.setCode("1");

        final com.mercadopago.model.Cause cause2 = new com.mercadopago.model.Cause();
        cause2.setCode("2");

        List<com.mercadopago.model.Cause> causeList = new ArrayList<com.mercadopago.model.Cause>() {{
            add(cause1);
            add(cause2);
        }};

        com.mercadopago.model.ApiException apiException = new com.mercadopago.model.ApiException();
        apiException.setCause(causeList);

        assertFalse(apiException.containsCause("Some cause"));
    }

    @Test
    public void whenCauseIsInListOfCausesThenContainsCauseShouldBeTrue() {

        final com.mercadopago.model.Cause cause1 = new com.mercadopago.model.Cause();
        cause1.setCode("1");

        final com.mercadopago.model.Cause cause2 = new com.mercadopago.model.Cause();
        cause2.setCode("Some cause");

        List<com.mercadopago.model.Cause> causeList = new ArrayList<Cause>() {{
            add(cause1);
            add(cause2);
        }};

        com.mercadopago.model.ApiException apiException = new com.mercadopago.model.ApiException();
        apiException.setCause(causeList);

        assertTrue(apiException.containsCause("Some cause"));
    }

}
