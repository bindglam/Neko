package com.bindglam.neko.api.content.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public sealed interface CustomItemProperties {
    @NotNull ItemType type();

    @Nullable Component name();

    @Nullable List<Component> lore();

    @NotNull NamespacedKey model();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements CustomItemProperties, com.bindglam.neko.api.content.Builder<CustomItemProperties> {
        private ItemType type;
        private Component name;
        private List<Component> lore;
        private NamespacedKey model;

        private Builder() {
        }


        public Builder type(ItemType type) {
            this.type = type;
            return this;
        }

        public Builder name(Component name) {
            this.name = name;
            return this;
        }

        public Builder lore(List<Component> lore) {
            this.lore = lore;
            return this;
        }

        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }


        @Override
        public @NotNull CustomItemProperties build() {
            if(type == null)
                throw new IllegalStateException("Item type can not be null!");

            return this;
        }


        @Override
        public @NotNull ItemType type() {
            return type;
        }

        @Override
        public @Nullable Component name() {
            return name;
        }

        @Override
        public @Nullable List<Component> lore() {
            return lore;
        }

        @Override
        public @NotNull NamespacedKey model() {
            return model;
        }
    }
}
