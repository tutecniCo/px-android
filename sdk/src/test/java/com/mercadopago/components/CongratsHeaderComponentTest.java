package com.mercadopago.components;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderComponentTest {
    @Test
    public void whenCongratsDoesNotHaveSubtitleThenHasSubtitleFalse() {
        CongratsHeaderComponent congratsHeaderComponent = new CongratsHeaderComponent("Oa");
        Assert.assertFalse(congratsHeaderComponent.hasSubtitle());
    }

    @Test
    public void whenCongratsHasSubtitleThenShowSubtitle() {
        CongratsHeaderComponent congratsHeaderComponent = new CongratsHeaderComponent("Oa", "Subtitulo");
        Assert.assertTrue(congratsHeaderComponent.hasSubtitle());
    }

    @Test
    public void whenCongratsHasTitleThenShowtTtle() {
        CongratsHeaderComponent congratsHeaderComponent = new CongratsHeaderComponent("Oa", "Subtitulo");
        Assert.assertEquals("Oa", congratsHeaderComponent.getTitle());
    }
}
