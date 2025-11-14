package com.bindglam.neko.api.content.item;

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
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class CustomItem implements Item, Packable {
    private final NamespacedKey key;
    private final ItemProperties properties;
    private final EventHandler eventHandler;

    public CustomItem(NamespacedKey key, ItemProperties itemProperties, EventHandler eventHandler) {
        this.key = key;
        this.properties = itemProperties;
        this.eventHandler = eventHandler;
    }

    public CustomItem(NamespacedKey key, ItemProperties itemProperties) {
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
    @NotNull
    public NamespacedKey getKey() {
        return key;
    }
}
