package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.feature.event.ItemStackGenerationEvent;
import io.github.bindglam.neko.platform.PlatformAdapter;
import io.github.bindglam.neko.platform.PlatformItemStack;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public final class ItemBuilder {
    public static final Key NEKO_ITEM_KEY = Key.key(Constants.MOD_ID, "item");

    private ItemBuilder() {
    }

    public static PlatformItemStack create(@NotNull Item item) {
        var itemStack = item.properties().type().createItemStack();

        Component displayName = item.properties().name();
        if (displayName == null)
            displayName = Component.translatable(item).color(NamedTextColor.WHITE);
        if (displayName.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
            displayName = displayName.decoration(TextDecoration.ITALIC, false);
        itemStack.name(displayName);

        itemStack.lore(item.properties().lore());

        itemStack.persistentDataContainer(persistentDataContainer ->
                persistentDataContainer.set(NEKO_ITEM_KEY, PlatformAdapter.adapter().persistentDataType(String.class).orElseThrow(), item.key().asString()));

        item.featureEventBus().call(new ItemStackGenerationEvent(itemStack));

        return itemStack;
    }
}
