package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public sealed interface BlockProperties {
    @NotNull NamespacedKey model();

    @NotNull Factory<BlockRenderer, Block> renderer();

    float hardness();

    @Nullable CorrectTools correctTools();

    @NotNull List<Enchantment> blacklistEnchantments();

    @Nullable Drops drops();

    @Nullable Sounds sounds();


    record Impl(NamespacedKey model, Factory<BlockRenderer, Block> renderer, float hardness, CorrectTools correctTools, List<Enchantment> blacklistEnchantments, Drops drops, Sounds sounds) implements BlockProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder implements com.bindglam.neko.api.utils.Builder<BlockProperties> {
        private NamespacedKey model;
        private Factory<BlockRenderer, Block> renderer;
        private float hardness;
        private CorrectTools correctTools;
        private final List<Enchantment> blacklistEnchantments = new ArrayList<>();
        private Drops drops;
        private Sounds sounds;

        private Builder() {
        }


        public Builder model(@NotNull NamespacedKey model) {
            this.model = model;
            return this;
        }

        public Builder renderer(@NotNull Factory<BlockRenderer, Block> renderer) {
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

        public Builder blacklistEnchantments(@NotNull List<Enchantment> blacklistEnchantments) {
            this.blacklistEnchantments.addAll(blacklistEnchantments);
            return this;
        }

        public Builder blacklistEnchantments(@NotNull Enchantment... enchantment) {
            this.blacklistEnchantments.addAll(Arrays.stream(enchantment).toList());
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

            return new Impl(model, renderer, hardness, correctTools, blacklistEnchantments, drops, sounds);
        }
    }
}
