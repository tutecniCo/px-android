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

    public static IdentificationType getById(String id) {
        switch (id) {
            case "RUT":
                return new IdentificationType("RUT", "RUT", "string", 7, 20);
            case "CPF":
                return new IdentificationType("CPF", "CPF", "number", 11, 11);
            default:
                return new IdentificationType("DNI", "DNI", "number", 7, 8);
        }
    }
}
