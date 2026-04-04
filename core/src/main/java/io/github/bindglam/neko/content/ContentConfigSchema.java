package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.Objects;

public final class ContentConfigSchema implements ConfigSchema {
    @Override
    public Result validate(ConfigurationNode config) {
        Result result = new Result();

        if(!Key.parseable(Objects.requireNonNull(config.key()).toString()))
            result.failed("Invalid content key");

        String typeId = config.node("type").getString();
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

        var featuresSection = config.node("features");
        if (featuresSection != null) {
            featuresSection.childrenMap().forEach((featureKey, featureConfig) -> {
                String featureId = featureConfig.node("id").getString();
                if (featureId == null) {
                    result.failed("Invalid feature configuration schema in " + config.key());
                    return;
                }
                if(!Key.parseable(featureId)) {
                    result.failed("Invalid feature id in " + config.key());
                    return;
                }

                FeatureFactory<?> factory = RegistryManager.GlobalRegistries.registries().features()
                        .get(Key.key(featureId))
                        .orElse(null);
                if(factory == null) {
                    result.failed("Unknown feature '" + featureId + "' in " + config.key());
                    return;
                }
            });
        }

        return result;
    }
}
