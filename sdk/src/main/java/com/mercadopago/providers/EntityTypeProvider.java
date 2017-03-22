package com.mercadopago.providers;

import com.mercadopago.mvp.ResourcesProvider;
import com.mercadopago.util.StepManagerUtil;

import java.util.List;

/**
 * Created by marlanti on 3/21/17.
 */

public interface EntityTypeProvider extends ResourcesProvider {
    List<String> getEntityTypes();
}
