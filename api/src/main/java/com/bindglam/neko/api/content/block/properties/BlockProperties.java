package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record BlockProperties(@NotNull NamespacedKey model, @NotNull Factory<BlockRenderer, Block> renderer, float hardness, float blastResistance,
                              @Nullable CorrectTools correctTools, @NotNull @Unmodifiable List<Enchantment> blacklistEnchantments, @Nullable Drops drops, @Nullable Sounds sounds) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<BlockProperties> {
        private NamespacedKey model;
        private Factory<BlockRenderer, Block> renderer;
        private float hardness = 1f;
        private float blastResistance = 6f;
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

        public Builder blastResistance(float blastResistance) {
            this.blastResistance = blastResistance;
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
            return new BlockProperties(model, renderer, hardness, blastResistance, correctTools, blacklistEnchantments, drops, sounds);
        }
    }
}
