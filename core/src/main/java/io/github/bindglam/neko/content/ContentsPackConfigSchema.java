package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

public final class ContentsPackConfigSchema implements ConfigSchema {
    @Override
    public @NotNull Result validate(@NotNull ConfigurationNode config) {
        Result result = new Result();

        String id = config.node("id").getString();
        if (id == null) {
            result.failed("Missing id");
        } else {
            if (!Key.parseableValue(id))
                result.failed("Invalid id");
        }

        String version = config.node("version").getString();
        if (version == null)
            result.failed("Missing version");

        String author = config.node("author").getString();
        if (author == null)
            result.failed("Missing author");

        return result;
    }
}
