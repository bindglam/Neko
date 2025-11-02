package com.bindglam.neko.api.pack.minecraft.model;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.minecraft.Float2;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public record ModelData(
        String credit,
        @SerializedName("texture_size") Float2 textureSize,
        Map<String, String> textures,
        List<Element> elements,
        @SerializedName("gui_light") GuiLight guiLight,
        Map<ModelDisplay.Type, ModelDisplay> display
) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        byte[] data = GsonUtils.GSON.toJson(this).getBytes();
        zipper.addFile(path, new PackFile(() -> data, data.length));
    }

    public enum GuiLight {
        @SerializedName("front")
        FRONT
    }
}
