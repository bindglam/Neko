package com.bindglam.neko.api.pack.minecraft.item;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import org.jetbrains.annotations.ApiStatus;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public record EquipmentData(Map<String, List<Model>> layers) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(StandardCharsets.UTF_8), -1));
    }

    public record Model(String texture) {
    }
}
