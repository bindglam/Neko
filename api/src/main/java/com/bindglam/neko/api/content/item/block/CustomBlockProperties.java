package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CustomBlockProperties(
        @NotNull Key blockModel,
        @NotNull MechanismFactory mechanismFactory,
        float hardness,
        @Nullable CorrectTools correctTools,
        @Nullable Drops drops
) {

    public record CorrectTools(
            @Nullable List<Tag<Material>> tags,
            @Nullable List<ItemType> items
    ) {
        public boolean isCorrectTool(ItemStack itemStack) {
            if(tags != null) {
                if(tags.stream().anyMatch((tag) -> tag.isTagged(itemStack.getType())))
                    return true;
            }

            if(items != null) {
                return items.stream().anyMatch((type) -> itemStack.getType().asItemType() == type);
            }

            return false;
        }
    }

    public record Drops(
            @Nullable List<DropData> dataList
    ) {
        public record DropData(
                @Nullable ItemType item,
                int experience,
                float chance
        ) {
        }
    }
}
