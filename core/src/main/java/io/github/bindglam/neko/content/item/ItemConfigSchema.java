package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.config.ConfigSchema;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

public final class ItemConfigSchema implements ConfigSchema {
    @Override
    public @NotNull Result validate(@NotNull ConfigurationNode config) {
        Result result = new Result();

        if(!config.hasChild("properties"))
            result.failed("Missing properties section");

        return result;
    }
}
