package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.content.AbstractContent;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.platform.PlatformAdapter;
import io.github.bindglam.neko.platform.PlatformItemStack;
import io.github.bindglam.neko.platform.PlatformPersistentDataContainer;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class ItemImpl extends AbstractContent implements Item {
    @Getter @Accessors(fluent = true)
    private final ItemProperties properties;
    @Getter @Accessors(fluent = true)
    private final ImmutableItemStack itemStack;

    public ItemImpl(@NotNull Key key,
                    @NotNull ItemProperties properties,
                    @NotNull List<FeatureBuilder> features) {
        super(key, features);
        this.properties = properties;
        this.itemStack = ImmutableItemStack.of(ItemBuilder.create(this));
    }

    @Override
    public boolean isSimilar(@NotNull PlatformItemStack itemStack) {
        boolean[] result = new boolean[] { false };
        itemStack.persistentDataContainer(persistentDataContainer ->
                result[0] = Objects.equals(key().asString(), persistentDataContainer.get(ItemBuilder.NEKO_ITEM_KEY, PlatformAdapter.adapter().persistentDataType(String.class).orElseThrow())));
        return result[0];
    }

    @Override
    public @NotNull String translationKey() {
        return "item." + key().namespace() + "." + key().value();
    }

    @Override
    public @NotNull Item asItem() {
        return this;
    }
}
