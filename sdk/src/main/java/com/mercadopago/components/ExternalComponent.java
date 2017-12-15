package com.mercadopago.components;

import android.support.annotation.NonNull;

/**
 * Created by nfortuna on 12/13/17.
 */

public class ExternalComponent extends Component<Object> {
    public ExternalComponent() {
        //Pass empty object as props, external objects should not have props because are not known from the SDK side.
        super(new Object());
    }
}