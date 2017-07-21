package com.mercadopago.mocks;

import com.mercadopago.model.CardInfo;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.utils.ResourcesUtil;

/**
 * Created by marlanti on 7/21/17.
 */

public class CardInfos {
    public static CardInfo getFrontSecurityCodeCardInfoMLA() {
        String json = ResourcesUtil.getStringResource("card_info_amex_MLA.json");
        return JsonUtil.getInstance().fromJson(json, CardInfo.class);
    }

    public static CardInfo getBackSecurityCodeCardInfoMLA() {
        String json = ResourcesUtil.getStringResource("card_info_visa_MLA.json");
        return JsonUtil.getInstance().fromJson(json, CardInfo.class);
    }
}
