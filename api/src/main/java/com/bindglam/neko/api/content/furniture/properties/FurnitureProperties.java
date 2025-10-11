package com.bindglam.neko.api.content.furniture.properties;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public sealed interface FurnitureProperties {
    @NotNull Model model();


    record Impl(Model model) implements FurnitureProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder implements com.bindglam.neko.api.utils.Builder<FurnitureProperties> {
        private Model model;


        public Builder model(Model model) {
            this.model = model;
            return this;
        }


        @Override
        public @NotNull FurnitureProperties build() {
            Validate.notNull(model, "Furniture model can not be null");

            return new Impl(model);
        }
    }
}
