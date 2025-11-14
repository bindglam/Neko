package com.bindglam.neko.api.content.furniture;

import com.bindglam.neko.api.content.EventContainer;
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.ItemLike;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Experimental
public interface Furniture extends Keyed, ItemLike, Translatable, EventContainer {
    NamespacedKey NEKO_FURNITURE_PDC_KEY = new NamespacedKey("neko", "furniture");
    NamespacedKey NEKO_FURNITURE_LIST_PDC_KEY = new NamespacedKey("neko", "furniture-list");

    @NotNull FurnitureDisplay place(@NotNull Location location);

    void destroy(@NotNull Location location);

    boolean isSame(@NotNull Location location);

    @Nullable FurnitureDisplay display(@NotNull Location location);

    @NotNull FurnitureProperties properties();

    @Override
    default @Nullable Item asItem() {
        return BuiltInRegistries.ITEMS.getOrNull(getKey());
    }

    @Override
    default @NotNull String translationKey() {
        return "furniture." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
