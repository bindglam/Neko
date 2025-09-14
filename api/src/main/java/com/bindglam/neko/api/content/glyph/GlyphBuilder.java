package com.bindglam.neko.api.content.glyph;

public sealed interface GlyphBuilder {
    int offsetX();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements GlyphBuilder {
        private int offsetX;

        private Builder() {
        }


        public Builder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }


        @Override
        public int offsetX() {
            return offsetX;
        }
    }
}
