package com.bindglam.neko.api.content.furniture.properties;

import org.bukkit.NamespacedKey;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@ApiStatus.Experimental
public sealed interface Model {
    @NotNull NamespacedKey model();

    @NotNull Transformation transformation();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements Model {
        private NamespacedKey model;
        private Transformation transformation = new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1f), new Quaternionf());


        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder transformation(Transformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder translation(Vector3f translation) {
            this.transformation.getTranslation().set(translation);
            return this;
        }

        public Builder rotation(Quaternionf rotation) {
            this.transformation.getRightRotation().set(rotation);
            return this;
        }

        public Builder scale(Vector3f scale) {
            this.transformation.getScale().set(scale);
            return this;
        }



        @Override
        public @NotNull NamespacedKey model() {
            return model;
        }

        @Override
        public @NotNull Transformation transformation() {
            return new Transformation(new Vector3f(transformation.getTranslation()), new Quaternionf(transformation.getLeftRotation()), new Vector3f(transformation.getScale()), new Quaternionf(transformation.getRightRotation()));
        }
    }
}
