package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;

public final class ContentsPackConfigSchema implements ConfigSchema {
    @Override
    public Result validate(ConfigurationSection config) {
        Result result = new Result();

        String id = config.getString("id");
        if (id == null) {
            result.failed("Missing id");
        } else {
            if (!Key.parseableValue(id))
                result.failed("Invalid id");
        }

        String version = config.getString("version");
        if (version == null)
            result.failed("Missing version");

        String author = config.getString("author");
        if (author == null)
            result.failed("Missing author");

        return result;
    }
}
