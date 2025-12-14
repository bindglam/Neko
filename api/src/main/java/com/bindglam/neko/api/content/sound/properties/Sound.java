package com.bindglam.neko.api.content.sound.properties;

import org.jetbrains.annotations.NotNull;

public record Sound(String file, float volume, float pitch, int weight, boolean stream, int attenuationDistance, boolean preload) {

    public com.bindglam.neko.api.pack.minecraft.sound.Sound toResourcePack() {
        return new com.bindglam.neko.api.pack.minecraft.sound.Sound(
                file,
                volume,
                pitch,
                weight,
                stream,
                attenuationDistance,
                preload
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<Sound> {
        private String file;
        private float volume = 1f;
        private float pitch = 1f;
        private int weight = 1;
        private boolean stream = true;
        private int attenuationDistance = 16;
        private boolean preload = false;

        private Builder() {
        }


        public Builder file(String file) {
            this.file = file;
            return this;
        }

        public Builder volume(float volume) {
            this.volume = volume;
            return this;
        }

        public Builder pitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder stream(boolean stream) {
            this.stream = stream;
            return this;
        }

        public Builder attenuationDistance(int attenuationDistance) {
            this.attenuationDistance = attenuationDistance;
            return this;
        }

        public Builder preload(boolean preload) {
            this.preload = preload;
            return this;
        }


        @Override
        public @NotNull Sound build() {
            return new Sound(file, volume, pitch, weight, stream, attenuationDistance, preload);
        }
    }
}
