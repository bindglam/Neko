package com.bindglam.neko.api.pack.minecraft.model;

import com.bindglam.neko.api.pack.minecraft.Float3;
import com.bindglam.neko.api.pack.minecraft.Float4;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public record Element(
        Float3 from,
        Float3 to,
        Rotation rotation,
        Map<UVFace, Face> faces
) {
    public record Rotation(
            float angle,
            Axis axis,
            Float3 origin
    ) {
    }

    public record Face(
            Float4 uv,
            String texture
    ) {
    }
}