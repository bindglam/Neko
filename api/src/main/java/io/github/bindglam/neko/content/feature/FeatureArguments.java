package io.github.bindglam.neko.content.feature;

import it.unimi.dsi.fastutil.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record FeatureArguments(@NotNull Map<String, String> map) {
    public @Nullable String get(@NotNull String name) {
        return map.get(name);
    }

    public @NotNull String getOrDefault(@NotNull String name, @NotNull String defaultValue) {
        @Nullable String value = get(name);
        if(value == null)
            return defaultValue;
        return value;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final Map<String, String> map = new HashMap<>();

        private Builder() {
        }

        public @NotNull Builder argument(@NotNull String name, @NotNull String value) {
            map.put(name, value);
            return this;
        }

        public @NotNull Builder arguments(@NotNull List<@NotNull Pair<@NotNull String, @NotNull String>> pairs) {
            pairs.forEach(pair -> argument(pair.key(), pair.value()));
            return this;
        }

        public @NotNull FeatureArguments build() {
            return new FeatureArguments(map);
        }
    }
}
