package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.feature.FeatureArguments;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.platform.PlatformItemType;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Platforms;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class ItemType implements ContentType<Item> {
    public static final Key KEY = Key.key(Constants.PLUGIN_ID, "item");

    private static final ConfigSchema ITEM_CONFIG_SCHEMA = new ItemConfigSchema();

    public static Key key() {
        return KEY;
    }

    @Override
    public @NotNull String id() {
        return "item";
    }

    @Override
    public @NotNull Class<Item> clazz() {
        return Item.class;
    }

    @Override
    public boolean load(@NotNull Registries registries, @NotNull ConfigurationNode config) {
        Logger logger = Platforms.logger();

        try {
            ConfigSchema.Result validationResult = ITEM_CONFIG_SCHEMA.validate(config);
            if(validationResult.isSuccess()) {
                Key key = Key.key(config.key().toString());

                ItemProperties properties = loadProperties(Objects.requireNonNull(config.node("properties")));

                List<FeatureBuilder> features = new ArrayList<>();
                ConfigurationNode featuresSection = config.node("features");
                if (featuresSection != null) {
                    featuresSection.childrenList().forEach(featureConfig -> {
                        FeatureFactory<?> factory = RegistryManager.GlobalRegistries.registries().features()
                                .get(Key.key(Objects.requireNonNull(featureConfig.node("id").getString())))
                                .orElseThrow(() -> new IllegalStateException("Unknown feature in " + key.asString()));

                        FeatureArguments.Builder arguments = FeatureArguments.builder();
                        featureConfig.node("arguments").childrenMap().forEach((name, valueConfig) -> {
                            arguments.argument(name.toString(), Objects.requireNonNull(valueConfig.getString()));
                        });

                        features.add(new FeatureBuilder(factory, arguments.build()));
                    });
                }

                registries.item().register(key, entry -> {
                    entry.key(key);
                    entry.properties(properties);
                    entry.features(features);
                });

                return true;
            } else {
                logger.warning("Failed to load '" + config.key() + "'");
                validationResult.getErrors().forEach(error -> logger.warning("  " + error));
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static ItemProperties loadProperties(@NotNull ConfigurationNode config) {
        var type = config.hasChild("type") ? PlatformItemType.get(Key.key(Objects.requireNonNull(config.node("type").getString()))).orElse(null) : null;

        var name = config.hasChild("name") ? MiniMessage.miniMessage().deserialize(Objects.requireNonNull(config.node("name").getString())) : null;

        List<Component> lore;
        try {
            lore = Objects.requireNonNull(config.node("lore").getList(String.class)).stream()
                    .map(MiniMessage.miniMessage()::deserialize)
                    .toList();
        } catch (SerializationException e) {
            lore = List.of();
        }

        return ItemProperties.builder()
                .type(type)
                .name(name)
                .lore(lore)
                .build();
    }
}
