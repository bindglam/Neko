package com.bindglam.neko.api.pack.minecraft.sound;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record Sound(
        String name,
        float volume,
        float pitch,
        int weight,
        boolean stream,
        @SerializedName("attenuation_distance") int attenuationDistance,
        boolean preload
) {
}
