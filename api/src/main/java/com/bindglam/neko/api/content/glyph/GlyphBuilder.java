package com.bindglam.neko.api.content.glyph;

public sealed interface GlyphBuilder {
    int offsetX();

    boolean shadow();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements GlyphBuilder {
        private int offsetX = 0;
        private boolean shadow = false;

        private Builder() {
        }


        public Builder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder shadow(boolean shadow) {
            this.shadow = shadow;
            return this;
        }


        @Override
        public int offsetX() {
            return offsetX;
        }

        @Override
        public boolean shadow() {
            return shadow;
        }
    }
}
