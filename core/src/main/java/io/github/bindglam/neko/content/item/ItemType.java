package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.feature.FeatureArguments;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Plugins;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class ItemType implements ContentType<Item> {
    public static final Key KEY = Key.key(Constants.PLUGIN_ID, "item");

    private static final ConfigSchema ITEM_CONFIG_SCHEMA = new ItemConfigSchema();

    private static final Material DEFAULT_MATERIAL = Material.PAPER;

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
    public boolean load(@NotNull Registries registries, @NotNull ConfigurationSection config) {
        Logger logger = Plugins.logger();

        try {
            ConfigSchema.Result validationResult = ITEM_CONFIG_SCHEMA.validate(config);
            if(validationResult.isSuccess()) {
                Key key = Key.key(config.getName());

                ItemProperties properties = loadProperties(Objects.requireNonNull(config.getConfigurationSection("properties")));

                List<FeatureBuilder> features = new ArrayList<>();
                ConfigurationSection featuresSection = config.getConfigurationSection("features");
                if (featuresSection != null) {
                    featuresSection.getKeys(false).stream().map(it -> Objects.requireNonNull(featuresSection.getConfigurationSection(it))).forEach(featureConfig -> {
                        FeatureFactory<?> factory = RegistryManager.GlobalRegistries.registries().features()
                                .get(Key.key(Objects.requireNonNull(featureConfig.getString("id"))))
                                .orElseThrow(() -> new IllegalStateException("Unknown feature in " + key.asString()));

                        FeatureArguments arguments = new FeatureArguments(featureConfig.getConfigurationSection("arguments"));

                        features.add(new FeatureBuilder(factory, arguments));
                    });
                }

                registries.item().register(key, entry -> {
                    entry.key(key);
                    entry.properties(properties);
                    entry.features(features);
                });

                return true;
            } else {
                logger.warning("Failed to load '" + config.getName() + "'");
                validationResult.getErrors().forEach(error -> logger.warning("  " + error));
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static ItemProperties loadProperties(@NotNull ConfigurationSection config) {
        String typeString = config.getString("type");
        Material type = typeString != null ? Material.valueOf(typeString) : DEFAULT_MATERIAL;

        String nameString = config.getString("name");
        var name = nameString != null ? MiniMessage.miniMessage().deserialize(nameString) : null;

        List<String> loreStrings = config.getStringList("lore");
        var lore = loreStrings.stream()
                .map(MiniMessage.miniMessage()::deserialize)
                .toList();

        return ItemProperties.builder()
                .type(type)
                .name(name)
                .lore(lore)
                .build();
    }
}
