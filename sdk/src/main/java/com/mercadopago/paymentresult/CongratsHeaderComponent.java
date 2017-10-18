package com.mercadopago.paymentresult;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponent extends Component<PaymentResultProps> {

    private final String title;
    private final SubtitleComponent subtitleComponent;

    public CongratsHeaderComponent(PaymentResultProps props, ActionDispatcher dispatcher) {
        super(dispatcher);
        this.title = props.title;
        if (props.subtitle != null) {
            this.subtitleComponent = new SubtitleComponent(props.subtitle, dispatcher);
        } else {
            this.subtitleComponent = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean hasSubtitle() {
        return subtitleComponent != null;
    }

    public SubtitleComponent getSubtitleComponent() {
        return subtitleComponent;
    }

    @Override
    public void setProps(PaymentResultProps props) {

    }
}
