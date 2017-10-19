package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponent extends Component<PaymentResultProps> {

    private SubtitleComponent subtitleComponent;
    private PaymentResultProps props;

    public CongratsHeaderComponent(@NonNull final PaymentResultProps props, @NonNull final ActionDispatcher dispatcher) {
        super(dispatcher);
        this.setProps(props);
    }

    public String getTitle() {
        return this.props.title;
    }

    public boolean hasSubtitle() {
        return subtitleComponent != null;
    }

    public SubtitleComponent getSubtitleComponent() {
        return subtitleComponent;
    }

    @Override
    public void setProps(@NonNull final PaymentResultProps props) {
        // hay diferencias ? evitar render si son iguales !!! deep compare
        // o usar persisten data structures
        this.props = props;
        if (props.subtitle != null) {
            this.subtitleComponent = new SubtitleComponent(props.subtitle, getDispatcher());
        } else {
            this.subtitleComponent = null;
        }
    }
}
