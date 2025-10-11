package com.bindglam.neko.api.content.item.properties;

import org.bukkit.inventory.meta.components.FoodComponent;
import org.jetbrains.annotations.NotNull;

public record Food(int nutrition, float saturation, boolean canAlwaysEat) {

    public void apply(@NotNull FoodComponent component) {
        component.setNutrition(nutrition());
        component.setSaturation(saturation());
        component.setCanAlwaysEat(canAlwaysEat());
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<Food> {
        private int nutrition;
        private float saturation;
        private boolean canAlwaysEat;

        private Builder() {
        }


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
        public @NotNull Food build() {
            return new Food(nutrition, saturation, canAlwaysEat);
        }
    }
}
