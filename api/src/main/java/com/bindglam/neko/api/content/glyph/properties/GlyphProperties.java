package com.bindglam.neko.api.content.glyph.properties;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public record GlyphProperties(@NotNull NamespacedKey texture, int offsetY, int scale) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<GlyphProperties> {
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
            return new GlyphProperties(texture, offsetY, scale);
        }
    }
}
