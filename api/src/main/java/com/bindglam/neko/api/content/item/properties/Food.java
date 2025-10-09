package com.bindglam.neko.api.content.item.properties;

import org.bukkit.inventory.meta.components.FoodComponent;
import org.jetbrains.annotations.NotNull;

public sealed interface Food {
    int nutrition();

    float saturation();

    boolean canAlwaysEat();

    default void apply(@NotNull FoodComponent component) {
        component.setNutrition(nutrition());
        component.setSaturation(saturation());
        component.setCanAlwaysEat(canAlwaysEat());
    }


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements Food {
        private int nutrition;
        private float saturation;
        private boolean canAlwaysEat;

        public Builder nutrition(int nutrition) {
            this.nutrition = nutrition;
            return this;
        }

        public Builder saturation(float saturation) {
            this.saturation = saturation;
            return this;
        }

        public Builder canAlwaysEat(boolean canAlwaysEat) {
            this.canAlwaysEat = canAlwaysEat;
            return this;
        }


        @Override
        public int nutrition() {
            return nutrition;
        }

        @Override
        public float saturation() {
            return saturation;
        }

        @Override
        public boolean canAlwaysEat() {
            return canAlwaysEat;
        }
    }
}
