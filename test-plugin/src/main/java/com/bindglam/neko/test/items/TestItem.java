package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestItem extends CustomItem {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "testitem");

    public TestItem() {
        super(KEY, ItemProperties.builder()
                .clientsideLore((itemStack, player) -> List.of(Component.text("테스트 아이템이다. " + player.getName() + "님아.")))
                .model(new NamespacedKey("defaultassets", "item/testitem"))
                .armor(Armor.builder()
                        .slot(EquipmentSlot.HEAD)
                        .isDispensable(true)
                        .isSwappable(true)
                        .isDamageOnHurt(true)
                        .isEquipOnInteract(true)
                        .build())
                .build());
    }

    @Override
    public EventState onUse(Player player, @NotNull ItemStack itemStack) {
        player.sendMessage(Component.text("냠냠 쩝쩝").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
        return EventState.CANCEL;
    }
}
