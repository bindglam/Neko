package com.bindglam.neko.api.content;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MechanismFactory<T> {
    @NotNull Mechanism<T> create(T object);

    @NotNull Class<T> type();


    @SuppressWarnings("unchecked")
    default @Nullable <t, F extends MechanismFactory<t>> F as(Class<t> clazz) {
        if(clazz == type())
            return (F) this;
        return null;
    }
}
