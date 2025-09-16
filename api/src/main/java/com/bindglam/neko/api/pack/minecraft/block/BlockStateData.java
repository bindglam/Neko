package com.bindglam.neko.api.pack.minecraft.block;

import java.util.Map;

public record BlockStateData(
        Map<String, Variant> variants
) {
    public record Variant(
            String model
    ) {
    }
}
