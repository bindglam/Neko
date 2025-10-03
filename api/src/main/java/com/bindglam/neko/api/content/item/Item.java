package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Item extends Keyed, ItemStackHolder, Translatable {
    NamespacedKey NEKO_ITEM_PDC_KEY = new NamespacedKey("neko", "item");

    default EventState onUse(Player player, @NotNull ItemStack itemStack) {
        return EventState.CONTINUE;
    }

    @NotNull ItemProperties properties();

    @Override
    default @NotNull ItemStack itemStack() {
        return ItemBuilder.build(this);
    }

    @Override
    default boolean isSame(ItemStack other) {
        return Objects.equals(other.getPersistentDataContainer().get(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING), getKey().toString());
    }

    @Override
    default @NotNull String translationKey() {
        return "item." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
