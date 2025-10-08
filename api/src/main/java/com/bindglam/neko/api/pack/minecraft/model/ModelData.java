package com.bindglam.neko.api.pack.minecraft.model;

import com.bindglam.neko.api.pack.minecraft.Float2;
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
) {
    public enum GuiLight {
        @SerializedName("front")
        FRONT
    }
}
