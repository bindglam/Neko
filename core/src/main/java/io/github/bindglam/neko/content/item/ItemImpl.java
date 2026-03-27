package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.AbstractContent;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ItemImpl extends AbstractContent implements Item {
    private final ItemProperties properties;
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
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        String itemKey = meta.getPersistentDataContainer().get(ItemBuilder.NEKO_ITEM_KEY, PersistentDataType.STRING);
        return key().asString().equals(itemKey);
    }

    @Override
    public @NotNull ItemProperties properties() {
        return properties;
    }

    @Override
    public @NotNull ImmutableItemStack itemStack() {
        return itemStack;
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
