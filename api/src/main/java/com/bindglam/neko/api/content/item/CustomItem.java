package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.item.EquipmentData;
import com.bindglam.neko.api.pack.minecraft.item.ItemData;
import com.google.gson.Gson;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CustomItem implements Item, Packable {
    private static final Gson GSON = new Gson();

    private final NamespacedKey key;
    private final ItemProperties properties;

    public CustomItem(NamespacedKey key, ItemProperties itemProperties) {
        this.key = key;
        this.properties = itemProperties;
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        buildModel(zipper);

        buildArmor(zipper);
    }

    private void buildModel(PackZipper zipper) {
        byte[] data = GSON.toJson(new ItemData(new ItemData.BasicModel(properties.model().asString()))).getBytes();

        String filePath = "assets/" + key.namespace() + "/items/" + key.value() + ".json";

        zipper.addFile(filePath, new PackFile(() -> data, data.length));
    }

    private void buildArmor(PackZipper zipper) {
        Armor armor = properties.armor();
        if(armor == null) return;

        NamespacedKey model = armor.model();
        if(model == null) return;

        String filePath = "assets/" + model.namespace() + "/equipment/" + model.value() + ".json";
        if(zipper.file(filePath) != null) return;

        List<EquipmentData.Model> modelData = List.of(new EquipmentData.Model(model.toString()));

        byte[] data = GSON.toJson(new EquipmentData(Map.of(
                "horse_body", modelData,
                "humanoid", modelData,
                "humanoid_leggings", modelData
        ))).getBytes();

        zipper.addFile(filePath, new PackFile(() -> data, data.length));
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
