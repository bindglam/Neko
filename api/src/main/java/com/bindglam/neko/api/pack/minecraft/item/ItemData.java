package com.bindglam.neko.api.pack.minecraft.item;

import com.google.gson.annotations.SerializedName;

public record ItemData(
        Model model
) {
    public static class Model {
        private final Type type;

        public Model(Type type) {
            this.type = type;
        }

        public Type type() {
            return type;
        }

        public enum Type {
            @SerializedName("minecraft:model")
            MODEL
        }
    }

    public static class BasicModel extends Model {
        private final String model;

        public BasicModel(String model) {
            super(Type.MODEL);
            this.model = model;
        }

        public String model() {
            return model;
        }
    }
}
