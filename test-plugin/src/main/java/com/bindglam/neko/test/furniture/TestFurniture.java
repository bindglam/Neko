package com.bindglam.neko.test.furniture;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.furniture.CustomFurniture;
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties;
import com.bindglam.neko.api.content.furniture.properties.Model;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.joml.Vector3f;

public class TestFurniture extends CustomFurniture {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "test_furniture");

    public TestFurniture() {
        super(KEY, FurnitureProperties.builder()
                .model(Model.builder()
                        .model(new NamespacedKey("defaultassets", "furniture/test_furniture"))
                        .translation(new Vector3f(-0.5f, 0f, 0f)))
                .build());
    }

    @Override
    public EventState onInteract(Player player, Location location) {
        player.sendMessage(Component.text("앙"));
        return EventState.CANCEL;
    }
}
