package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.registry.Registries;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ContentType<T extends Content> {
    @NotNull String id();

    @NotNull Class<T> clazz();

    @NotNull LoadResult load(@NotNull Registries registries, @NotNull ConfigurationSection config);

    @Getter
    @Accessors(fluent = true)
    final class LoadResult {
        private static final LoadResult SUCCESS = new LoadResult(null);

        private final @Nullable String errorMessage;

        private LoadResult(@Nullable String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return errorMessage == null;
        }

        public boolean isFailure() {
            return errorMessage != null;
        }

        public static @NotNull LoadResult success() {
            return SUCCESS;
        }

        public static @NotNull LoadResult failure(@NotNull String message) {
            return new LoadResult(message);
        }
    }
}
