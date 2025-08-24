package com.bindglam.neko.api.registry;

import com.bindglam.neko.api.item.CustomItem;

public interface BuiltInRegistries {
    Registry<CustomItem> ITEMS = empty();

    private static <T> Registry<T> empty() {
        return new ScalableRegistry<>();
    }
}
