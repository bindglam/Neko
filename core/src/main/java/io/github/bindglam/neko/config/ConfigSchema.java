package io.github.bindglam.neko.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public interface ConfigSchema {
    Result validate(ConfigurationSection config);

    @Getter
    final class Result {
        private boolean isSuccess = true;
        private final List<String> errors = new ArrayList<>();

        public void failed(String error) {
            errors.add(error);

            isSuccess = false;
        }
    }
}
