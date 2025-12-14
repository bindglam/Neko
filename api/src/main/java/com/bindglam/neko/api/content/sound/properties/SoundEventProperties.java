package com.bindglam.neko.api.content.sound.properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public record SoundEventProperties(@Nullable String subtitle, @Unmodifiable List<Sound> sounds) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<SoundEventProperties> {
        private String subtitle;
        private final List<Sound> sounds = new ArrayList<>();

        private Builder() {
        }


        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder sound(Sound sound) {
            this.sounds.add(sound);
            return this;
        }

        public Builder sounds(List<Sound> sounds) {
            this.sounds.clear();
            this.sounds.addAll(sounds);
            return this;
        }


        @Override
        public @NotNull SoundEventProperties build() {
            return new SoundEventProperties(subtitle, List.copyOf(sounds));
        }
    }
}
