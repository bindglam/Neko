package com.bindglam.neko.api.content.furniture;

import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import com.bindglam.neko.api.pack.minecraft.item.ItemData;
import com.bindglam.neko.api.utils.GsonUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApiStatus.Experimental
public class CustomFurniture implements Furniture, Packable {
    private final NamespacedKey key;
    private final FurnitureProperties properties;

    public CustomFurniture(NamespacedKey key, FurnitureProperties properties) {
        this.key = key;
        this.properties = properties;
    }

    @Override
    public void pack(@NotNull PackZipper zipper) {
        AtlasesMaker.addAllFromModel(properties.model().model(), zipper);

        byte[] data = GsonUtils.GSON.toJson(new ItemData(new ItemData.BasicModel(properties.model().model().toString()))).getBytes();

        String filePath = "assets/" + key.namespace() + "/items/" + key.value() + ".json";

        zipper.addFile(filePath, new PackFile(() -> data, data.length));
    }

    @Override
    public @NotNull FurnitureDisplay place(@NotNull Location location) {
        location.getBlock().setType(Material.BARRIER);

        ItemDisplay display = location.getWorld().spawn(location.toCenterLocation(), ItemDisplay.class);
        display.setTransformation(properties.model().transformation());

        display.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_PDC_KEY, PersistentDataType.STRING, key.toString());
        display.setPersistent(true);

        ItemStack itemStack = new ItemStack(Material.PAPER);
        itemStack.editMeta((meta) -> meta.setItemModel(key));
        display.setItemStack(itemStack);

        Chunk chunk = location.getChunk();
        if(!chunk.getPersistentDataContainer().has(Furniture.NEKO_FURNITURE_LIST_PDC_KEY))
            chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), new ArrayList<>());
        List<String> list = new ArrayList<>(Objects.requireNonNull(chunk.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings())));
        list.add(display.getUniqueId().toString());
        chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), list);

        return new FurnitureDisplay(this, location.toBlockLocation(), display);
    }

    @Override
    public void destroy(@NotNull Location location) {
        FurnitureDisplay display = display(location);
        if(display == null) return;

        Chunk chunk = location.getChunk();
        List<String> uuids = new ArrayList<>(Objects.requireNonNull(chunk.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings())));
        uuids.remove(display.display().getUniqueId().toString());
        chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), uuids);

        display.display().remove();
        location.getBlock().setType(Material.AIR);
    }

    @Override
    public boolean isSame(@NotNull Location location) {
        return display(location) != null;
    }

    @Override
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

    @Override
    public @NotNull FurnitureProperties properties() {
        return properties;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }
}
