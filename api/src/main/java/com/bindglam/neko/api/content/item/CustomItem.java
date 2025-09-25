package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.Builder;
import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.item.ItemData;
import com.google.gson.Gson;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomItem implements Keyed, ItemStackHolder, Packable {
    private static final NamespacedKey NEKO_ITEM_PDC_KEY = new NamespacedKey("neko", "item");
    private static final Gson GSON = new Gson();

    private final NamespacedKey key;
    private final CustomItemProperties properties;

    private ItemStack itemStack;

    public CustomItem(NamespacedKey key, CustomItemProperties itemProperties) {
        this.key = key;
        this.properties = itemProperties;

        buildItemStack();
    }

    private void buildItemStack() {
        itemStack = properties.type().createItemStack();

        itemStack.editMeta((meta) -> {
            meta.itemName(properties.name());
            meta.lore(properties.lore());
            meta.setItemModel(key);
        });

        itemStack.editPersistentDataContainer((dataContainer) -> {
            dataContainer.set(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING, key.toString());
        });
    }

    public EventState onUse(Player player, @NotNull ItemStack itemStack) {
        return EventState.CONTINUE;
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        byte[] data = GSON.toJson(new ItemData(new ItemData.BasicModel(properties.model().asString()))).getBytes();

        String filePath = "assets/" + key.namespace() + "/items/" + key.value() + ".json";

        zipper.addFile(filePath, new PackFile(() -> data, data.length));
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean isSame(ItemStack other) {
        return Objects.equals(other.getPersistentDataContainer().get(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING), key.toString());
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    public @NotNull CustomItemProperties itemProperties() {
        return properties;
    }
}
