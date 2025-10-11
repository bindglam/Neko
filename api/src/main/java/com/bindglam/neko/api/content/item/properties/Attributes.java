package com.bindglam.neko.api.content.item.properties;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record Attributes(@NotNull Map<Attribute, AttributeModifier> modifiers, boolean resetWhenApply) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<Attributes> {
        private final Map<Attribute, AttributeModifier> modifiers = new HashMap<>();
        private boolean resetWhenApply = false;

        private Builder() {
        }


        public Builder modifier(Attribute attribute, double value, EquipmentSlotGroup slot) {
            modifiers.put(attribute, new AttributeModifier(NamespacedKey.minecraft("base_" + attribute.getKey().value()), value, AttributeModifier.Operation.ADD_NUMBER, slot));
            return this;
        }

        public Builder modifier(Attribute attribute, double value) {
            return modifier(attribute, value, EquipmentSlotGroup.ANY);
        }

        public Builder resetWhenApply(boolean resetWhenApply) {
            this.resetWhenApply = resetWhenApply;
            return this;
        }


        @Override
        public @NotNull Attributes build() {
            return new Attributes(modifiers, resetWhenApply);
        }
    }
}
