package com.bindglam.neko.api.content.item.properties;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public record ItemProperties(@NotNull ItemType type, int durability, @Nullable Component name, @Nullable List<Component> lore, @Nullable BiFunction<ItemStack, Player, List<Component>> clientsideLore,
                             @NotNull NamespacedKey model, @Nullable Armor armor, @Nullable Attributes attributes, @Nullable Food food, @NotNull Map<Enchantment, Integer> enchantments) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<ItemProperties> {
        private ItemType type = ItemType.PAPER;
        private int durability = 0;
        private Component name;
        private List<Component> lore;
        private BiFunction<ItemStack, Player, List<Component>> clientsideLore;
        private NamespacedKey model;
        private Armor armor;
        private Attributes attributes;
        private Food food;
        private final Map<Enchantment, Integer> enchantments = new HashMap<>();

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

        public Builder enchantments(Map<Enchantment, Integer> enchantments) {
            this.enchantments.putAll(enchantments);
            return this;
        }

        public Builder enchantment(Enchantment enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }


        @Override
        public @NotNull ItemProperties build() {
            return new ItemProperties(type, durability, name, lore, clientsideLore, model, armor, attributes, food, enchantments);
        }
    }
}
