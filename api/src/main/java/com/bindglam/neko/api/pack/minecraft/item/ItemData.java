package com.bindglam.neko.api.pack.minecraft.item;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record ItemData(
        Model model
) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(), -1));
    }

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
