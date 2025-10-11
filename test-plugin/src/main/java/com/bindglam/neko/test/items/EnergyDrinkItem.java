package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.Food;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class EnergyDrinkItem extends CustomItem {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "energy_drink");

    public EnergyDrinkItem() {
        super(KEY, ItemProperties.builder()
                .type(ItemType.APPLE)
                .lore(List.of(Component.text("진짜 로어")))
                .clientsideLore((itemStack, player) -> {
                    Objects.requireNonNull(itemStack.getItemMeta().lore()).forEach(player::sendMessage);

                    return List.of(Component.text("테스트 아이템이다. " + player.getName() + "님아."));
                })
                .model(new NamespacedKey("defaultassets", "item/energy_drink"))
                .food(Food.builder()
                        .nutrition(10)
                        .saturation(10)
                        .canAlwaysEat(true))
                .enchantment(Enchantment.MENDING, 1)
                .build());
    }

    @Override
    public EventState onUse(Player player, @NotNull ItemStack itemStack) {
        player.sendMessage(Component.text("냠냠 쩝쩝").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
        return EventState.CONTINUE;
    }
}
