package com.mercadopago.model;

import android.content.Context;

import com.mercadopago.tracking.model.Fingerprint;

/**
 * Created by mromar on 10/24/17.
 */

public class Device {
    //// FIXME: 10/25/17 VER DE TRAER FINGERPRINT TAMBIÉN
    Fingerprint fingerprint;

    public Device(Context context) {
        this.fingerprint = new Fingerprint(context);
    }
}
