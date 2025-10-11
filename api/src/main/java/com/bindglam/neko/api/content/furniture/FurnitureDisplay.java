package com.bindglam.neko.api.content.furniture;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiStatus.Experimental
public record FurnitureDisplay(Furniture furniture, Location location, ItemDisplay display) {
    public void applyTransformationModifier(Transformation modifier) {
        Transformation result = furniture.properties().model().transformation();

        result.getTranslation().add(modifier.getTranslation());
        result.getLeftRotation().mul(modifier.getLeftRotation());
        result.getScale().add(modifier.getScale());
        result.getRightRotation().mul(modifier.getRightRotation());

        display.setTransformation(result);
    }

    @Override
    public Location location() {
        return location.clone();
    }

    public void destroy() {
        Chunk chunk = location.getChunk();
        List<String> uuids = new ArrayList<>(Objects.requireNonNull(chunk.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings())));
        uuids.remove(display.getUniqueId().toString());
        chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), uuids);

        display.remove();
        location.getBlock().setType(Material.AIR);
    }

    public static FurnitureDisplay create(Furniture furniture, Location location) {
        location.getBlock().setType(Material.BARRIER);

        ItemDisplay display = location.getWorld().spawn(location.toCenterLocation(), ItemDisplay.class);
        display.setTransformation(furniture.properties().model().transformation());

        display.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_PDC_KEY, PersistentDataType.STRING, furniture.getKey().toString());
        display.setPersistent(true);

        ItemStack itemStack = new ItemStack(Material.PAPER);
        itemStack.editMeta((meta) -> meta.setItemModel(furniture.getKey()));
        display.setItemStack(itemStack);

        Chunk chunk = location.getChunk();
        if(!chunk.getPersistentDataContainer().has(Furniture.NEKO_FURNITURE_LIST_PDC_KEY))
            chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), new ArrayList<>());
        List<String> list = new ArrayList<>(Objects.requireNonNull(chunk.getPersistentDataContainer().get(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings())));
        list.add(display.getUniqueId().toString());
        chunk.getPersistentDataContainer().set(Furniture.NEKO_FURNITURE_LIST_PDC_KEY, PersistentDataType.LIST.strings(), list);

        return new FurnitureDisplay(furniture, location.toBlockLocation(), display);
    }
}
