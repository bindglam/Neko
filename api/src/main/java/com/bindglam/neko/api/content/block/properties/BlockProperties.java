package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    record Sounds(
            @NotNull Key placeSound,
            @NotNull Key breakSound
    ) {}
}
