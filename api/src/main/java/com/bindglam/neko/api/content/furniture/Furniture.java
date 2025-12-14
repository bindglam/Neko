package com.bindglam.neko.api.content.furniture;

import com.bindglam.neko.api.content.EventContainer;
import com.bindglam.neko.api.content.EventHandler;
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.ItemLike;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import com.bindglam.neko.api.pack.minecraft.item.ItemData;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApiStatus.Experimental
public class Furniture implements Keyed, ItemLike, Translatable, EventContainer, Packable {
    public static final NamespacedKey NEKO_FURNITURE_PDC_KEY = new NamespacedKey("neko", "furniture");
    public static final NamespacedKey NEKO_FURNITURE_LIST_PDC_KEY = new NamespacedKey("neko", "furniture-list");

    private final NamespacedKey key;
    private final FurnitureProperties properties;
    private final EventHandler eventHandler;

    public Furniture(NamespacedKey key, FurnitureProperties properties, EventHandler eventHandler) {
        this.key = key;
        this.properties = properties;
        this.eventHandler = eventHandler;
    }

    public Furniture(NamespacedKey key, FurnitureProperties properties) {
        this(key, properties, EventHandler.EMPTY);
    }

    @Override
    public @NotNull EventHandler eventHandler() {
        return eventHandler;
    }

    @Override
    public void pack(@NotNull PackZipper zipper) {
        AtlasesMaker.addAllFromModel(properties.model().model(), zipper);

        String filePath = "assets/" + key.namespace() + "/items/" + key.value() + ".json";

        zipper.addComponent(filePath, new ItemData(new ItemData.BasicModel(properties.model().model().toString())));
    }

    public @NotNull FurnitureDisplay place(@NotNull Location location) {
        return FurnitureDisplay.create(this, location);
    }

    public void destroy(@NotNull Location location) {
        FurnitureDisplay display = display(location);
        if(display == null) return;

        display.destroy();
    }

    public boolean isSame(@NotNull Location location) {
        return display(location) != null;
    }

    public @Nullable FurnitureDisplay display(@NotNull Location location) {
        Chunk chunk = location.getChunk();
        if(!chunk.getPersistentDataContainer().has(Furniture.NEKO_FURNITURE_LIST_PDC_KEY))
            return null;

        List<String> uuids = new ArrayList<>(Objects.requireNonNull(chunk.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings())));
        for (int i = uuids.size() - 1; i >= 0; i--) {
            UUID uuid = UUID.fromString(uuids.get(i));
            Entity entity = Bukkit.getEntity(uuid);
            if(entity == null) {
                uuids.remove(i);
                continue;
            }
            if(!entity.getPersistentDataContainer().has(Furniture.NEKO_FURNITURE_PDC_KEY)) {
                uuids.remove(i);
                continue;
            }

            Location loc1 = entity.getLocation().toCenterLocation();
            Location loc2 = location.toCenterLocation();
            boolean isSameLocation = Double.compare(loc1.x(), loc2.x()) == 0 && Double.compare(loc1.y(), loc2.y()) == 0 && Double.compare(loc1.z(), loc2.z()) == 0;

            if (isSameLocation && Objects.equals(entity.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_PDC_KEY, PersistentDataType.STRING), key.toString())) {
                chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), uuids);

                return new FurnitureDisplay(this, location.toBlockLocation(), (ItemDisplay) entity);
            }
        }

        chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), uuids);

        return null;
    }

    public @NotNull FurnitureProperties properties() {
        return properties;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @Override
    public @Nullable Item asItem() {
        return BuiltInRegistries.ITEMS.get(getKey()).orElse(null);
    }

    @Override
    public @NotNull String translationKey() {
        return "furniture." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
