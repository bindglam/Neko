package com.bindglam.neko.api.pack.minecraft.sound;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@ApiStatus.Internal
public record SoundEvent(
        @Nullable String subtitle,
        @Unmodifiable List<Sound> sounds
) {
}
