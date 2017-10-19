package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponent extends Component<PaymentResultProps> {

    private SubtitleComponent subtitleComponent;

    public CongratsHeaderComponent(@NonNull final ActionDispatcher dispatcher) {
        super(dispatcher);
    }

//    public CongratsHeaderComponent() {
//        super(props);
//    }

//    public CongratsHeaderComponent(@NonNull final PaymentResultProps props, @NonNull final ActionDispatcher dispatcher) {
//        super(props, dispatcher);
//    }

    public String getTitle() {
        return this.getProps().title;
    }

    public boolean hasSubtitle() {
        return subtitleComponent != null;
    }

    public SubtitleComponent getSubtitleComponent() {
        return subtitleComponent;
    }

    @Override
    public void applyProps(@NonNull final PaymentResultProps props) {
        // hay diferencias ? evitar render si son iguales !!! deep compare
        // o usar persisten data structures
        this.setProps(props);

        if (props.subtitle != null) {
            this.subtitleComponent = new SubtitleComponent(props.subtitle, getDispatcher());
        } else {
            this.subtitleComponent = null;
        }
    }
}
