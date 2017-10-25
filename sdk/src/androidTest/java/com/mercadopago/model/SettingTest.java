package com.mercadopago.model;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mercadopago.CheckoutActivity;
import com.mercadopago.test.StaticMock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingTest {

    @Rule
    public ActivityTestRule<CheckoutActivity> mTestRule = new ActivityTestRule<>(CheckoutActivity.class, false, false);

    @Test
    public void whenValidBinThenGetSetting() {
        com.mercadopago.model.PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());
        com.mercadopago.model.Setting setting = com.mercadopago.model.Setting.getSettingByBin(paymentMethod.getSettings(), "466057");
        assertTrue(setting != null);
    }

    @Test
    public void whenInvalidBinThenReturnNullSetting() {
        PaymentMethod paymentMethod = StaticMock.getPaymentMethod(InstrumentationRegistry.getContext());
        com.mercadopago.model.Setting setting = com.mercadopago.model.Setting.getSettingByBin(paymentMethod.getSettings(), "888888");
        assertTrue(setting == null);
    }

    @Test
    public void whenNoSettingsThenReturnNullSetting() {
        com.mercadopago.model.Setting setting = com.mercadopago.model.Setting.getSettingByBin(new ArrayList<com.mercadopago.model.Setting>(), "466057");
        assertTrue(setting == null);
        setting = com.mercadopago.model.Setting.getSettingByBin(null, "466057");
        assertTrue(setting == null);
    }
}

