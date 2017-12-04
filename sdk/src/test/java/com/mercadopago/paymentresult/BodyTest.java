package com.mercadopago.paymentresult;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.constants.ProcessingModes;
import com.mercadopago.mocks.Instructions;
import com.mercadopago.mocks.PaymentResults;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.components.Body;
import com.mercadopago.paymentresult.components.BodyError;
import com.mercadopago.paymentresult.props.PaymentResultBodyProps;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by vaserber on 22/11/2017.
 */

public class BodyTest {

    private ActionDispatcher dispatcher;
    private PaymentResultProvider paymentResultProvider;
    private PaymentMethodProvider paymentMethodProvider;

    @Before
    public void setup() {
        dispatcher = mock(ActionDispatcher.class);
        paymentResultProvider = mock(PaymentResultProvider.class);
        paymentMethodProvider = mock(PaymentMethodProvider.class);
    }

    @Test
    public void testBodyHasInstructions() {
        final Body body = new Body(getBodyPropsForInstructions(Instructions.getRapipagoInstruction()),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasInstructions());
        Assert.assertNotNull(body.getInstructionsComponent());
    }

    @Test
    public void testInstructionsHasValidProps() {
        final Instruction instruction = Instructions.getRapipagoInstruction();
        final Body body = new Body(getBodyPropsForInstructions(instruction),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        com.mercadopago.paymentresult.components.Instructions instructionsComponent = body.getInstructionsComponent();
        Assert.assertEquals(instructionsComponent.props.instruction, instruction);
    }

    private PaymentResultBodyProps getBodyPropsForInstructions(Instruction instruction) {
        return new PaymentResultBodyProps.Builder()
                .setStatus(Payment.StatusCodes.STATUS_PENDING)
                .setStatusDetail(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)
                .setProcessingMode(ProcessingModes.AGGREGATOR)
                .setInstruction(instruction)
                .build();

    }

    @Test
    public void testBodyHasErrorWithContingency() {
        final PaymentResult paymentResult = PaymentResults.getStatusInProcessContingencyPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionOther() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedOtherPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionInsufficientAmount() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedInsufficientAmountPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionCallForAuth() {
        final PaymentResult paymentResult = PaymentResults.getStatusCallForAuthPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBoleto() {
        final PaymentResult paymentResult = PaymentResults.getBoletoRejectedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledDate() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledDatePaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledSecurityCode() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledSecuPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledForm() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledFormPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyErrorHasValidPropsForInsufficientData() {
        final PaymentResult paymentResult = PaymentResults.getBoletoRejectedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
                dispatcher, paymentResultProvider, paymentMethodProvider);

        BodyError bodyError = body.getBodyErrorComponent();
        Assert.assertEquals(bodyError.props.status, paymentResult.getPaymentStatus());
        Assert.assertEquals(bodyError.props.statusDetail, paymentResult.getPaymentStatusDetail());
        Assert.assertEquals(bodyError.props.paymentMethodName, paymentResult.getPaymentData().getPaymentMethod().getName());
    }

    private PaymentResultBodyProps getBodyPropsForOnPayment(PaymentResult paymentResult) {
        return new PaymentResultBodyProps.Builder()
                .setStatus(paymentResult.getPaymentStatus())
                .setStatusDetail(paymentResult.getPaymentStatusDetail())
                .setPaymentData(paymentResult.getPaymentData())
                .build();
    }
}
