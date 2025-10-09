package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.properties.Armor
import com.bindglam.neko.api.content.item.properties.Attributes
import com.bindglam.neko.api.content.item.properties.Food
import com.bindglam.neko.api.content.item.properties.ItemProperties
import com.bindglam.neko.config.ItemTypeConfigurable
import com.bindglam.neko.config.KeyConfigurable
import com.bindglam.neko.config.SoundConfigurable
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

object CustomItemPropertiesConfigurable : Configurable<ItemProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): ItemProperties? = config?.let { ItemProperties.builder()
        .type(ItemTypeConfigurable.load(config.getString("type"))?.itemStack()?.type?.asItemType() ?: ItemType.PAPER)
        .durability(config.getInt("durability"))
        .name(config.getRichMessage("name"))
        .lore(config.getStringList("lore").map { MiniMessage.miniMessage().deserialize(it) })
        .model(KeyConfigurable.load(config.getString("model")!!)!!)
        .armor(ArmorConfigurable.load(config.getConfigurationSection("armor")))
        .attributes(AttributesConfigurable.load(config.getConfigurationSection("attributes")))
        .food(FoodConfigurable.load(config.getConfigurationSection("food")))
        .build()
    }

    private object ArmorConfigurable : Configurable<Armor, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Armor? = config?.let { Armor.builder()
            .slot(EquipmentSlot.valueOf(config.getString("slot")!!))
            .equipSound(SoundConfigurable.load(config.getString("equip-sound")))
            .model(KeyConfigurable.load(config.getString("model")))
            .cameraOverlay(KeyConfigurable.load(config.getString("camera-overlay")))
            .allowedEntities(if(config.contains("allowed-entities")) config.getStringList("allowed-entities").map { EntityType.valueOf(it) } else null)
            .isDispensable(config.getBoolean("is-dispensable"))
            .isSwappable(config.getBoolean("is-swappable"))
            .isDamageOnHurt(config.getBoolean("damage-on-hurt"))
            .isEquipOnInteract(config.getBoolean("equip-on-interact"))
        }
    }

    private object AttributesConfigurable : Configurable<Attributes, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Attributes? = config?.let {
            val builder = Attributes.builder()
                .resetWhenApply(config.getBoolean("reset-when-apply"))

            config.getConfigurationSection("modifiers")?.getKeys(false)?.forEach { key ->
                val attribute = RegistryAccess.registryAccess().getRegistry(RegistryKey.ATTRIBUTE).get(NamespacedKey.fromString(key)!!) ?: return@forEach
                val value = config.getDouble("modifiers.${key}.amount")
                val slot = config.getString("modifiers.${key}.slot")?.let { EquipmentSlotGroup.getByName(it) } ?: EquipmentSlotGroup.ANY

                builder.modifier(attribute, value, slot)
            }

            return@let builder
        }
    }

    private object FoodConfigurable : Configurable<Food, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Food? = config?.let { Food.builder()
            .nutrition(config.getInt("nutrition"))
            .saturation(config.getDouble("saturation").toFloat())
            .canAlwaysEat(config.getBoolean("can-always-eat"))
        }
    }
}