package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.ItemStackHolder;
import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public sealed interface CustomBlockProperties {
    @NotNull NamespacedKey model();

    @NotNull MechanismFactory mechanismFactory();

    float hardness();

    @Nullable CorrectTools correctTools();

    @Nullable Drops drops();

    @Nullable Sounds sounds();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements CustomBlockProperties {
        private NamespacedKey model;
        private MechanismFactory mechanismFactory;
        private float hardness;
        private CorrectTools correctTools;
        private Drops drops;
        private Sounds sounds;

        private Builder() {
        }


        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder mechanismFactory(MechanismFactory mechanismFactory) {
            this.mechanismFactory = mechanismFactory;
            return this;
        }

        public Builder hardness(float hardness) {
            this.hardness = hardness;
            return this;
        }

        public Builder correctTools(CorrectTools correctTools) {
            this.correctTools = correctTools;
            return this;
        }

        public Builder drops(Drops drops) {
            this.drops = drops;
            return this;
        }

        public Builder sounds(Sounds sounds) {
            this.sounds = sounds;
            return this;
        }


        @Override
        public @NotNull NamespacedKey model() {
            return model;
        }

        @Override
        public @NotNull MechanismFactory mechanismFactory() {
            return mechanismFactory;
        }

        @Override
        public float hardness() {
            return hardness;
        }

        @Override
        public @Nullable CorrectTools correctTools() {
            return correctTools;
        }

        @Override
        public @Nullable Drops drops() {
            return drops;
        }

        @Override
        public @Nullable Sounds sounds() {
            return sounds;
        }
    }


    record CorrectTools(
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

    record Drops(
            @Nullable List<DropData> dataList
    ) {
        public record DropData(
                @Nullable ItemStackHolder item,
                int experience,
                float chance
        ) {}
    }

    record Sounds(
            @NotNull Key placeSound,
            @NotNull Key breakSound
    ) {}
}
