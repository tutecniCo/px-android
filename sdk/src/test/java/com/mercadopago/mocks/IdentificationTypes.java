package com.mercadopago.mocks;

import com.mercadopago.model.IdentificationType;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.utils.ResourcesUtil;

/**
 * Created by marlanti on 7/7/17.
 */

public class IdentificationTypes {

    public static IdentificationType getIdentificationTypeMLA() {
        String json = ResourcesUtil.getStringResource("identification_type_MLA.json");
        return JsonUtil.getInstance().fromJson(json, IdentificationType.class);
    }
}
