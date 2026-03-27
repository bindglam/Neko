package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.ContentType;
import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.content.feature.FeatureArguments;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ItemType implements ContentType<Item> {
    public static final Key KEY = Key.key(Constants.PLUGIN_ID, "item");
    private static final Material DEFAULT_MATERIAL = Material.PAPER;

    public ItemType() {
    }

    public static Key key() {
        return KEY;
    }

    @Override
    public String id() {
        return "item";
    }

    @Override
    public Class<Item> clazz() {
        return Item.class;
    }

    @Override
    public LoadResult load(@NotNull Registries registries, @NotNull ConfigurationSection config) {
        try {
            Key key = Key.key(config.getName());

            ConfigurationSection propertiesSection = config.getConfigurationSection("properties");
            if (propertiesSection == null) {
                return LoadResult.failure("Missing properties section");
            }
            ItemProperties properties = loadProperties(propertiesSection);

            List<FeatureBuilder> features = new ArrayList<>();
            ConfigurationSection featuresSection = config.getConfigurationSection("features");
            if (featuresSection != null) {
                for (String featureKey : featuresSection.getKeys(false)) {
                    ConfigurationSection featureConfig = featuresSection.getConfigurationSection(featureKey);
                    if (featureConfig == null) {
                        continue;
                    }

                    String featureId = featureConfig.getString("id");
                    if (featureId == null) {
                        throw new IllegalArgumentException("No feature id in " + key.asString());
                    }

                    FeatureFactory<?> factory = RegistryManager.GlobalRegistries.registries().features()
                            .get(Key.key(featureId))
                            .orElseThrow(() -> new IllegalStateException("Unknown feature in " + key.asString()));

                    ConfigurationSection argsSection = featureConfig.getConfigurationSection("arguments");
                    FeatureArguments arguments = new FeatureArguments(argsSection);

                    features.add(new FeatureBuilder(factory, arguments));
                }
            }

            registries.item().register(key, entry -> {
                entry.key(key);
                entry.properties(properties);
                entry.features(features);
            });

            return LoadResult.success();
        } catch (Exception e) {
            return LoadResult.failure(e.getMessage() != null ? e.getMessage() : "Unknown error");
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
