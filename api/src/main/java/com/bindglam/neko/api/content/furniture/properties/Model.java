package com.bindglam.neko.api.content.furniture.properties;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public sealed interface Model {
    @NotNull NamespacedKey model();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements Model {
        private NamespacedKey model;


        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }


        @Override
        public @NotNull NamespacedKey model() {
            return model;
        }
    }
}
