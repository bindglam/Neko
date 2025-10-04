package com.bindglam.neko.api.content.furniture;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Furniture extends Keyed, Translatable {
    NamespacedKey NEKO_FURNITURE_PDC_KEY = new NamespacedKey("neko", "furniture");
    NamespacedKey NEKO_FURNITURE_LIST_PDC_KEY = new NamespacedKey("neko", "furniture-list");

    default EventState onInteract(Player player, Location location) {
        return EventState.CONTINUE;
    }

    void place(@NotNull Location location);

    void destroy(@NotNull Location location);

    boolean isSame(@NotNull Location location);

    @Nullable ItemDisplay display(@NotNull Location location);

    @NotNull FurnitureProperties properties();

    @Override
    default @NotNull String translationKey() {
        return "furniture." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
