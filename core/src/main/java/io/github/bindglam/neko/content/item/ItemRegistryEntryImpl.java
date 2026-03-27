package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import net.kyori.adventure.key.Key;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ItemRegistryEntryImpl implements ItemRegistryEntry {
    private Key key;
    private List<FeatureBuilder> features;
    private ItemProperties properties;

    public ItemRegistryEntryImpl() {
        this.features = List.of();
    }

    @Override
    public @NotNull ItemRegistryEntry key(@NotNull Key key) {
        this.key = key;
        return this;
    }

    @Override
    public @NotNull ItemRegistryEntry features(@NotNull List<FeatureBuilder> features) {
        this.features = features;
        return this;
    }

    @Override
    public @NotNull ItemRegistryEntry properties(@NotNull ItemProperties properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public @NotNull Item toValue() {
        Validate.notNull(key, "Key is null");
        Validate.notNull(properties, "Properties is null");

        return new ItemImpl(key, properties, features != null ? features : List.of());
    }
}
