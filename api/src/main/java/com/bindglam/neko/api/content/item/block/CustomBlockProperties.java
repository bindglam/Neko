package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.ItemStackHolder;
import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CustomBlockProperties(
        @NotNull Key model,
        @NotNull MechanismFactory mechanismFactory,
        float hardness,
        @Nullable CorrectTools correctTools,
        @Nullable Drops drops,
        @Nullable Sounds sounds
) {

    public record CorrectTools(
            @Nullable List<Tag<Material>> tags,
            @Nullable List<ItemStackHolder> items
    ) {
        public boolean isCorrectTool(ItemStack itemStack) {
            if(itemStack == null) return false;

            if(tags != null) {
                if(tags.stream().anyMatch((tag) -> tag.isTagged(itemStack.getType())))
                    return true;
            }

            if(items != null) {
                return items.stream().anyMatch((item) -> item.isSame(itemStack));
            }

            return false;
        }
    }

    public record Drops(
            @Nullable List<DropData> dataList
    ) {
        public record DropData(
                @Nullable ItemStackHolder item,
                int experience,
                float chance
        ) {
        }
    }

    public record Sounds(
            @NotNull Key placeSound,
            @NotNull Key breakSound
    ) {
    }
}
