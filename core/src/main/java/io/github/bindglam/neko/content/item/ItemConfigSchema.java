package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.config.ConfigSchema;
import org.bukkit.configuration.ConfigurationSection;

public final class ItemConfigSchema implements ConfigSchema {
    @Override
    public Result validate(ConfigurationSection config) {
        Result result = new Result();

        if(!config.contains("properties"))
            result.failed("Missing properties section");

        return result;
    }
}
