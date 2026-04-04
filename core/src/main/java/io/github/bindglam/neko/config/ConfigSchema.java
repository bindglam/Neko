package io.github.bindglam.neko.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.ArrayList;
import java.util.List;

public interface ConfigSchema {
    @NotNull Result validate(@NotNull ConfigurationNode config);

    @Getter
    final class Result {
        private boolean isSuccess = true;
        private final List<String> errors = new ArrayList<>();

        public void failed(@NotNull String error) {
            errors.add(error);

            isSuccess = false;
        }
    }
}
