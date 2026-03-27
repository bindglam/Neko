package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.feature.event.ItemStackGenerationEvent;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class ItemBuilder {
    public static final NamespacedKey NEKO_ITEM_KEY = new NamespacedKey(Constants.PLUGIN_ID, "item");

    private ItemBuilder() {
    }

    public static ItemStack create(@NotNull Item item) {
        ItemStack itemStack = new ItemStack(item.properties().type());

        itemStack.editMeta(meta -> {
            Component displayName = item.properties().name();
            if (displayName == null)
                displayName = Component.translatable(item).color(NamedTextColor.WHITE);
            if (displayName.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
                displayName = displayName.decoration(TextDecoration.ITALIC, false);
            meta.displayName(displayName);

            meta.lore(item.properties().lore());

            meta.getPersistentDataContainer().set(NEKO_ITEM_KEY, PersistentDataType.STRING, item.key().asString());
        });

        item.featureEventBus().call(new ItemStackGenerationEvent(itemStack));

        return itemStack;
    }
}
