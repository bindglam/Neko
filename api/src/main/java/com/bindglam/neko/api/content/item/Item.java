package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.EventContainer;
import com.bindglam.neko.api.content.EventHandler;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import com.bindglam.neko.api.pack.minecraft.item.EquipmentData;
import com.bindglam.neko.api.pack.minecraft.item.ItemData;
import com.bindglam.neko.api.utils.GsonUtils;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Item implements Keyed, ItemStackReference, ItemLike, Translatable, EventContainer, Packable {
    public static final NamespacedKey NEKO_ITEM_PDC_KEY = new NamespacedKey("neko", "item");

    private final NamespacedKey key;
    private final ItemProperties properties;
    private final EventHandler eventHandler;

    public Item(NamespacedKey key, ItemProperties itemProperties, EventHandler eventHandler) {
        this.key = key;
        this.properties = itemProperties;
        this.eventHandler = eventHandler;
    }

    public Item(NamespacedKey key, ItemProperties itemProperties) {
        this(key, itemProperties, EventHandler.EMPTY);
    }

    @Override
    public @NotNull EventHandler eventHandler() {
        return eventHandler;
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        buildModel(zipper);

        buildArmor(zipper);
    }

    private void buildModel(PackZipper zipper) {
        AtlasesMaker.addAllFromModel(properties.model(), zipper);

        String filePath = "assets/" + key.namespace() + "/items/" + key.value() + ".json";

        if(zipper.file(filePath) == null)
            zipper.addComponent(filePath, new ItemData(new ItemData.BasicModel(properties.model().asString())));
    }

    private void buildArmor(PackZipper zipper) {
        Armor armor = properties.armor();
        if(armor == null) return;

        NamespacedKey model = armor.model();
        if(model == null) return;

        String filePath = "assets/" + model.namespace() + "/equipment/" + model.value() + ".json";
        if(zipper.file(filePath) != null) return;

        List<EquipmentData.Model> modelData = List.of(new EquipmentData.Model(model.toString()));

        zipper.addFile(filePath, new PackFile(() -> GsonUtils.GSON.toJson(new EquipmentData(Map.of(
                "horse_body", modelData,
                "humanoid", modelData,
                "humanoid_leggings", modelData
        ))).getBytes(StandardCharsets.UTF_8), -1));
    }

    @NotNull
    public ItemProperties properties() {
        return properties;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return ItemBuilder.build(this);
    }

    @Override
    public boolean isSame(ItemStack other) {
        return Objects.equals(other.getPersistentDataContainer().get(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING), getKey().toString());
    }

    @Override
    public @NotNull Item asItem() {
        return this;
    }

    @Override
    @NotNull
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public @NotNull String translationKey() {
        return "item." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
