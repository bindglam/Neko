package com.bindglam.neko.api.content.item.properties;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public record Armor(@NotNull EquipmentSlot slot, @Nullable Sound equipSound, @Nullable NamespacedKey model, @Nullable NamespacedKey cameraOverlay,
                    @Nullable @Unmodifiable List<EntityType> allowedEntities, boolean isDispensable, boolean isSwappable, boolean isDamageOnHurt, boolean isEquipOnInteract) {

    public void apply(@NotNull EquippableComponent component) {
        component.setSlot(slot());
        component.setEquipSound(equipSound());
        component.setModel(model());
        component.setCameraOverlay(cameraOverlay());
        component.setAllowedEntities(allowedEntities());
        component.setDispensable(isDispensable());
        component.setSwappable(isSwappable());
        component.setDamageOnHurt(isDamageOnHurt());
        component.setEquipOnInteract(isEquipOnInteract());
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<Armor> {
        private EquipmentSlot slot;
        private Sound equipSound;
        private NamespacedKey model;
        private NamespacedKey cameraOverlay;
        private List<EntityType> allowedEntities;
        private boolean isDispensable;
        private boolean isSwappable;
        private boolean isDamageOnHurt;
        private boolean isEquipOnInteract;

        private Builder() {
        }
        

        public Builder slot(EquipmentSlot slot) {
            this.slot = slot;
            return this;
        }

        public Builder equipSound(Sound equipSound) {
            this.equipSound = equipSound;
            return this;
        }

        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder cameraOverlay(NamespacedKey cameraOverlay) {
            this.cameraOverlay = cameraOverlay;
            return this;
        }

        public Builder allowedEntities(List<EntityType> allowedEntities) {
            this.allowedEntities = allowedEntities;
            return this;
        }

        public Builder isDispensable(boolean isDispensable) {
            this.isDispensable = isDispensable;
            return this;
        }

        public Builder isSwappable(boolean isSwappable) {
            this.isSwappable = isSwappable;
            return this;
        }

        public Builder isDamageOnHurt(boolean isDamageOnHurt) {
            this.isDamageOnHurt = isDamageOnHurt;
            return this;
        }

        public Builder isEquipOnInteract(boolean isEquipOnInteract) {
            this.isEquipOnInteract = isEquipOnInteract;
            return this;
        }


        @Override
        public @NotNull Armor build() {
            return new Armor(slot, equipSound, model, cameraOverlay, allowedEntities, isDispensable, isSwappable, isDamageOnHurt, isEquipOnInteract);
        }
    }
}
