package com.mercadopago.paymentresult;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponent extends Component {

    private final String title;
    private final SubtitleComponent subtitleComponent;

    public CongratsHeaderComponent(String title, String subtitle, ActionDispatcher dispatcher) {
        super(dispatcher);
        this.title = title;
        if (subtitle != null) {
            this.subtitleComponent = new SubtitleComponent(subtitle, dispatcher);
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
}
