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

    int durability();

    @Nullable Component name();

    @Nullable List<Component> lore();

    @Nullable BiFunction<ItemStack, Player, List<Component>> clientsideLore();

    @NotNull NamespacedKey model();

    @Nullable Armor armor();

    @Nullable Attributes attributes();

    @Nullable Food food();


    record Impl(ItemType type, int durability, Component name, List<Component> lore, BiFunction<ItemStack, Player, List<Component>> clientsideLore, NamespacedKey model, Armor armor, Attributes attributes,
                Food food) implements ItemProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder implements com.bindglam.neko.api.utils.Builder<ItemProperties> {
        private ItemType type = ItemType.PAPER;
        private int durability = 0;
        private Component name;
        private List<Component> lore;
        private BiFunction<ItemStack, Player, List<Component>> clientsideLore;
        private NamespacedKey model;
        private Armor armor;
        private Attributes attributes;
        private Food food;

        private Builder() {
        }


        public Builder type(@NotNull ItemType type) {
            this.type = type;
            return this;
        }

        public Builder durability(int durability) {
            this.durability = durability;
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

        public Builder model(@NotNull NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder armor(Armor armor) {
            this.armor = armor;
            return this;
        }

        public Builder attributes(Attributes attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder food(Food food) {
            this.food = food;
            return this;
        }


        @Override
        public @NotNull ItemProperties build() {
            if(type == null)
                throw new IllegalStateException("Item type can not be null!");

            if(model == null)
                throw new IllegalStateException("Item model can not be null!");

            return new Impl(type, durability, name, lore, clientsideLore, model, armor, attributes, food);
        }
    }
}
