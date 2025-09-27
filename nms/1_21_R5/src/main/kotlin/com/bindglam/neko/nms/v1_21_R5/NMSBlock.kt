package com.bindglam.neko.nms.v1_21_R5

import com.bindglam.neko.api.content.item.block.CustomBlock
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import org.bukkit.block.BlockState
import org.bukkit.craftbukkit.block.CraftBlockStates
import java.util.*

class NMSBlock(private val customBlock: CustomBlock) : com.bindglam.neko.api.content.item.block.Block by customBlock {
    companion object {
        private val FROZEN_FIELD = MappedRegistry::class.java.getDeclaredField("frozen").apply { isAccessible = true }
        private val UNREGISTERED_INTRUSIVE_HOLDERS = MappedRegistry::class.java.getDeclaredField("unregisteredIntrusiveHolders").apply { isAccessible = true }

        fun resourceKey(block: CustomBlock): ResourceKey<Block> = ResourceKey.create(BuiltInRegistries.BLOCK.key(), block.key.toNMS())
    }

    private val nmsBlock by lazy { Block(Properties.of().setId(resourceKey(customBlock))) }

    fun register() {
        FROZEN_FIELD.set(BuiltInRegistries.BLOCK, false)
        UNREGISTERED_INTRUSIVE_HOLDERS.set(BuiltInRegistries.BLOCK, IdentityHashMap<Block, Holder.Reference<Block>>())

        val holder = Registry.registerForHolder(BuiltInRegistries.BLOCK, customBlock.key.toNMS(), nmsBlock)

        Block.BLOCK_STATE_REGISTRY.add(nmsBlock.defaultBlockState())

        FROZEN_FIELD.set(BuiltInRegistries.BLOCK, true)
    }

    override fun blockState(): BlockState = CraftBlockStates.getBlockState(nmsBlock.defaultBlockState(), null)
}