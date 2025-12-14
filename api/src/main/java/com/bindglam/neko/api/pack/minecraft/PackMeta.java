package com.bindglam.neko.api.pack.minecraft;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public record PackMeta(Pack pack, @Nullable Overlays overlays) implements PackComponent {
    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(), -1));
    }

    public record Pack(
            @SerializedName("pack_format") int packFormat,
            String description,
            @SerializedName("supported_formats") @Nullable @Unmodifiable List<Integer> supportedFormats,
            @SerializedName("min_format") @Nullable Integer minFormat,
            @SerializedName("max_format") @Nullable Integer maxFormat
    ) {
    }

    public record Overlays(
            @Unmodifiable List<Entry> entries
    ) {
        public record Entry(
                @Unmodifiable List<Integer> formats,
                String directory,
                @SerializedName("min_format") int minFormat,
                @SerializedName("max_format") int maxFormat
        ) {
        }
    }
}
