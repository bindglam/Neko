package com.bindglam.neko.api.content.glyph;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public sealed interface GlyphProperties {
    @NotNull NamespacedKey texture();

    int offsetY();

    int scale();


    record Impl(NamespacedKey texture, int offsetY, int scale) implements GlyphProperties {
    }

    static Builder builder() {
        return new Builder();
    }

    final class Builder implements com.bindglam.neko.api.content.Builder<GlyphProperties> {
        private NamespacedKey texture;
        private int offsetY;
        private int scale;

        private Builder() {
        }


        public Builder texture(NamespacedKey texture) {
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

            return new Impl(texture, offsetY, scale);
        }
    }
}
