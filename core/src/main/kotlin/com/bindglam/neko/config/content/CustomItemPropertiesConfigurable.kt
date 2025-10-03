package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.properties.Armor
import com.bindglam.neko.api.content.item.properties.Attributes
import com.bindglam.neko.api.content.item.properties.ItemProperties
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import com.bindglam.neko.utils.SOUND_CONFIGURABLE
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

class CustomItemPropertiesConfigurable : Configurable<ItemProperties, ConfigurationSection> {
    companion object {
        private val ARMOR_CONFIGURABLE = ArmorConfigurable()
        private val ATTRIBUTES_CONFIGURABLE = AttributesConfigurable()
    }

    override fun load(config: ConfigurationSection?): ItemProperties? = config?.let { ItemProperties.builder()
        .type(ITEM_TYPE_CONFIGURABLE.load(config.getString("type"))?.itemStack()?.type?.asItemType() ?: ItemType.PAPER)
        .durability(config.getInt("durability"))
        .name(config.getRichMessage("name"))
        .lore(config.getStringList("lore").map { MiniMessage.miniMessage().deserialize(it) })
        .model(KEY_CONFIGURABLE.load(config.getString("model")))
        .armor(ARMOR_CONFIGURABLE.load(config.getConfigurationSection("armor")))
        .attributes(ATTRIBUTES_CONFIGURABLE.load(config.getConfigurationSection("attributes")))
        .build()
    }

    private class ArmorConfigurable : Configurable<Armor, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Armor? = config?.let { Armor.builder()
            .slot(EquipmentSlot.valueOf(config.getString("slot")!!))
            .equipSound(SOUND_CONFIGURABLE.load(config.getString("equip-sound")))
            .model(KEY_CONFIGURABLE.load(config.getString("model")))
            .cameraOverlay(KEY_CONFIGURABLE.load(config.getString("camera-overlay")))
            .allowedEntities(if(config.contains("allowed-entities")) config.getStringList("allowed-entities").map { EntityType.valueOf(it) } else null)
            .isDispensable(config.getBoolean("is-dispensable"))
            .isSwappable(config.getBoolean("is-swappable"))
            .isDamageOnHurt(config.getBoolean("damage-on-hurt"))
            .isEquipOnInteract(config.getBoolean("equip-on-interact"))
        }
    }

    private class AttributesConfigurable : Configurable<Attributes, ConfigurationSection> {
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
}