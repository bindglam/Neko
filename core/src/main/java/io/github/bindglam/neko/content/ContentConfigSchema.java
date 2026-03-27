package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;

public final class ContentConfigSchema implements ConfigSchema {
    @Override
    public Result validate(ConfigurationSection config) {
        Result result = new Result();

        if(!Key.parseable(config.getName()))
            result.failed("Invalid content key");

        String typeId = config.getString("type");
        if (typeId == null) {
            result.failed("Missing type");
        } else {
            if (!Key.parseableValue(typeId)) {
                result.failed("Invalid type id");
            } else {
                ContentType<?> type = RegistryManager.GlobalRegistries.registries().types()
                        .get(Key.key(Constants.PLUGIN_ID, typeId))
                        .orElse(null);
                if(type == null)
                    result.failed("Unknown type");
            }
        }

        ConfigurationSection featuresSection = config.getConfigurationSection("features");
        if (featuresSection != null) {
            for (String featureKey : featuresSection.getKeys(false)) {
                ConfigurationSection featureConfig = Objects.requireNonNull(featuresSection.getConfigurationSection(featureKey));

                String featureId = featureConfig.getString("id");
                if (featureId == null) {
                    result.failed("Invalid feature configuration schema in " + config.getName());
                    continue;
                }
                if(!Key.parseable(featureId)) {
                    result.failed("Invalid feature id in " + config.getName());
                    continue;
                }

                FeatureFactory<?> factory = RegistryManager.GlobalRegistries.registries().features()
                        .get(Key.key(featureId))
                        .orElse(null);
                if(factory == null) {
                    result.failed("Unknown feature '" + featureId + "' in " + config.getName());
                    continue;
                }
            }
        }

        return result;
    }
}
