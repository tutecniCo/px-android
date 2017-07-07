package com.mercadopago.mocks;

import com.mercadopago.preferences.CheckoutPreference;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.utils.ResourcesUtil;

/**
 * Created by marlanti on 7/7/17.
 */

public class CheckoutPreferences {
    public static CheckoutPreference getCheckoutPreferenceMLA() {
        String json = ResourcesUtil.getStringResource("checkout_preference_MLA.json");
        return JsonUtil.getInstance().fromJson(json, CheckoutPreference.class);
    }
}
