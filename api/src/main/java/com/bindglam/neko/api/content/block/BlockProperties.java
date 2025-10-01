package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.item.ItemStackHolder;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public sealed interface BlockProperties {
    @NotNull NamespacedKey model();

    @NotNull Factory<BlockRenderer, Block> renderer();

    float hardness();

    @Nullable CorrectTools correctTools();

    boolean dropSilkTouch();

    @Nullable Drops drops();

    @Nullable Sounds sounds();


    record Impl(NamespacedKey model, Factory<BlockRenderer, Block> renderer, float hardness, CorrectTools correctTools, boolean dropSilkTouch, Drops drops, Sounds sounds) implements BlockProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder implements com.bindglam.neko.api.utils.Builder<BlockProperties> {
        private NamespacedKey model;
        private Factory<BlockRenderer, Block> renderer;
        private float hardness;
        private CorrectTools correctTools;
        private boolean dropSilkTouch;
        private Drops drops;
        private Sounds sounds;

        private Builder() {
        }


        public Builder model(NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder renderer(Factory<BlockRenderer, Block> renderer) {
            this.renderer = renderer;
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

        public Builder dropSilkTouch(boolean dropSilkTouch) {
            this.dropSilkTouch = dropSilkTouch;
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
        public @NotNull BlockProperties build() {
            if(model == null)
                throw new IllegalStateException("Block model can not be null!");

            if(renderer == null)
                throw new IllegalStateException("Block renderer can not be null!");

            return new Impl(model, renderer, hardness, correctTools, dropSilkTouch, drops, sounds);
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
