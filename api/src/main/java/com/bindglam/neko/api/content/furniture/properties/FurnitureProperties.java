package com.bindglam.neko.api.content.furniture.properties;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public record FurnitureProperties(@NotNull Model model) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements com.bindglam.neko.api.utils.Builder<FurnitureProperties> {
        private Model model;


        public Builder model(Model model) {
            this.model = model;
            return this;
        }


        @Override
        public @NotNull FurnitureProperties build() {
            return new FurnitureProperties(model);
        }
    }
}
