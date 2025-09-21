package com.bindglam.neko.api.pack.minecraft.block;

import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public record BlockStateData(
        Map<String, Variant> variants
) {
    public record Variant(
            String model
    ) {
    }
}
