package com.mercadopago.components;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponent {
    private final String title;
    private final SubtitleComponent subtitleComponent;

    public CongratsHeaderComponent(String title) {
        this.title = title;
        this.subtitleComponent = null;
    }

    public CongratsHeaderComponent(String title, String subtitle) {
        this.title = title;
        this.subtitleComponent = new SubtitleComponent(subtitle);
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
