package com.bindglam.neko.api.pack.minecraft;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@ApiStatus.Internal
public record AtlasesData(@Unmodifiable List<Source> sources) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(), -1));
    }

    public record Source(Type type, String resource) {
        public enum Type {
            @SerializedName("single")
            SINGLE
        }
    }
}
