package com.bindglam.neko.api.content.glyph;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public sealed interface GlyphProperties {
    @NotNull Key texture();

    int offsetY();

    int scale();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements GlyphProperties, com.bindglam.neko.api.content.Builder<GlyphProperties> {
        private Key texture;
        private int offsetY;
        private int scale;

        private Builder() {
        }


        public Builder texture(Key texture) {
            this.texture = texture;
            return this;
        }

        public Builder offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder scale(int scale) {
            this.scale = scale;
            return this;
        }


        @Override
        public @NotNull GlyphProperties build() {
            if(texture == null)
                throw new IllegalStateException("Texture can not be null");

            return this;
        }


        @Override
        public @NotNull Key texture() {
            return texture;
        }

        @Override
        public int offsetY() {
            return offsetY;
        }

        @Override
        public int scale() {
            return scale;
        }
    }
}
