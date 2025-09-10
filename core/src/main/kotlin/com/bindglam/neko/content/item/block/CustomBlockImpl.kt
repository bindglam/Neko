package com.bindglam.neko.content.item.block

import com.bindglam.neko.api.content.item.CustomItemProperties
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.item.block.CustomBlockProperties
import com.bindglam.neko.api.content.item.block.mechanism.Mechanism
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packable
import com.bindglam.neko.content.item.CustomItemImpl
import org.bukkit.NamespacedKey

class CustomBlockImpl(key: NamespacedKey, itemProperties: CustomItemProperties, private val blockProperties: CustomBlockProperties) : CustomItemImpl(key, itemProperties), CustomBlock {
    private val mechanism = blockProperties.mechanismFactory().create(this)

    override fun pack(zipper: PackZipper) {
        super.pack(zipper)

        if(mechanism is Packable)
            mechanism.pack(zipper)
    }

    override fun blockProperties(): CustomBlockProperties = blockProperties

    override fun mechanism(): Mechanism = mechanism
}