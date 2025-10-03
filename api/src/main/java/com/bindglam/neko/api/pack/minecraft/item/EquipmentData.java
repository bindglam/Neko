package com.bindglam.neko.api.pack.minecraft.item;

import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public record EquipmentData(Map<String, List<Model>> layers) {
    public record Model(String texture) {
    }
}
