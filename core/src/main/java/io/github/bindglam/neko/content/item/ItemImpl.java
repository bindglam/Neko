package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.AbstractContent;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
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
    public boolean isSimilar(@NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta())
            return false;
        return Objects.equals(key().asString(), itemStack.getItemMeta().getPersistentDataContainer().get(ItemBuilder.NEKO_ITEM_KEY, PersistentDataType.STRING));
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
