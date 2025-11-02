package com.bindglam.neko.api.pack.minecraft.item;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public record EquipmentData(Map<String, List<Model>> layers) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        byte[] data = GsonUtils.GSON.toJson(this).getBytes();
        zipper.addFile(path, new PackFile(() -> data, data.length));
    }

    public record Model(String texture) {
    }
}
