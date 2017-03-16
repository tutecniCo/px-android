package com.mercadopago.validators;

import android.text.TextUtils;

import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;

/**
 * Created by marlanti on 3/16/17.
 */

public class IdentificationValidator {

    public static boolean validateIdentification(Identification identification) {
        return validateIdentificationType(identification) && validateIdentificationNumber(identification);
    }

    public static boolean validateIdentificationType(Identification identification) {
        return (identification == null) ? false :
                        !TextUtils.isEmpty(identification.getType());
    }

    public static boolean validateIdentificationNumber(Identification identification) {
        return  (identification == null) ? false :
                        (!validateIdentificationType(identification)) ? false :
                                !TextUtils.isEmpty(identification.getNumber());
    }

    public static boolean validateIdentificationNumber(IdentificationType identificationType, Identification identification) {
        if (identificationType != null) {
            if (
                    (identification != null) &&
                    (identification.getNumber() != null)) {
                int len = identification.getNumber().length();
                Integer min = identificationType.getMinLength();
                Integer max = identificationType.getMaxLength();
                if ((min != null) && (max != null)) {
                    return ((len <= max) && (len >= min));
                } else {
                    return validateIdentificationNumber(identification);
                }
            } else {
                return false;
            }
        } else {
            return validateIdentificationNumber(identification);
        }
    }
}
