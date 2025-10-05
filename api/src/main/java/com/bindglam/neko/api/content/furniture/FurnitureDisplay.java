package com.bindglam.neko.api.content.furniture;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.ApiStatus;

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
}
