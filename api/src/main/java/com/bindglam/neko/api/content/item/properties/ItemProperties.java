package com.bindglam.neko.api.content.item.properties;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

public sealed interface ItemProperties {
    @NotNull ItemType type();

    @Nullable Component name();

    @Nullable List<Component> lore();

    @Nullable BiFunction<ItemStack, Player, List<Component>> clientsideLore();

    @NotNull NamespacedKey model();

    @Nullable Armor armor();


    record Impl(ItemType type, Component name, List<Component> lore, BiFunction<ItemStack, Player, List<Component>> clientsideLore, NamespacedKey model, Armor armor) implements ItemProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder implements com.bindglam.neko.api.utils.Builder<ItemProperties> {
        private ItemType type = ItemType.PAPER;
        private Component name;
        private List<Component> lore;
        private BiFunction<ItemStack, Player, List<Component>> clientsideLore;
        private NamespacedKey model;
        private Armor armor;

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

        public Builder clientsideLore(BiFunction<ItemStack, Player, List<Component>> function) {
            this.clientsideLore = function;
            return this;
        }

        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder armor(Armor armor) {
            this.armor = armor;
            return this;
        }


        @Override
        public @NotNull ItemProperties build() {
            if(type == null)
                throw new IllegalStateException("Item type can not be null!");

            return new Impl(type, name, lore, clientsideLore, model, armor);
        }
    }
}
