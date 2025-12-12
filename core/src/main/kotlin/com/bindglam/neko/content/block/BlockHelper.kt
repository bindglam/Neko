package com.bindglam.neko.content.block

import com.bindglam.neko.utils.CURRENT_TICK
import com.bindglam.neko.utils.blastResistance
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*


object BlockHelper {
    private val breakProgress = hashMapOf<UUID, Float>()
    private val lastBreakProgressUpdated = hashMapOf<UUID, Int>()

    private val lastPlaceBlock = hashMapOf<UUID, Int>()

    fun tick() {
        val forRemoval = arrayListOf<UUID>()
        lastBreakProgressUpdated.forEach { (uuid, lastTick) ->
            if(CURRENT_TICK - lastTick > 2) {
                forRemoval.add(uuid)
            }
        }

        forRemoval.forEach {
            breakProgress.remove(it)
            lastBreakProgressUpdated.remove(it)
        }
    }

    fun hasBreakProgress(player: Player): Boolean = breakProgress.contains(player.uniqueId)

    fun breakProgress(player: Player): Float = breakProgress.getOrDefault(player.uniqueId, 0f)

    fun updateBreakProgress(player: Player, speed: Float) {
        if(!hasBreakProgress(player))
            breakProgress[player.uniqueId] = 0f

        breakProgress[player.uniqueId] = breakProgress(player) + speed
        lastBreakProgressUpdated[player.uniqueId] = CURRENT_TICK
    }

    fun removeBreakProgress(player: Player) {
        breakProgress.remove(player.uniqueId)
    }

    fun lastPlaceBlock(player: Player): Int = lastPlaceBlock.computeIfAbsent(player.uniqueId) { 0 }

    fun updateLastPlaceBlock(player: Player) {
        lastPlaceBlock[player.uniqueId] = CURRENT_TICK
    }

    fun getBlocksToBlowUp(center: Location, power: Float, fromBlock: Boolean): List<Block> {
        fun generateRays(): List<org.bukkit.util.Vector> {
            val rays = ArrayList<org.bukkit.util.Vector>()
            val gridSize = 16

            for (x in 0..gridSize) {
                for (y in 0..gridSize) {
                    for (z in 0..gridSize) {
                        if (x == 0 || x == gridSize || y == 0 || y == gridSize || z == 0 || z == gridSize) {
                            val vec = org.bukkit.util.Vector(x.toDouble() - gridSize / 2, y.toDouble() - gridSize / 2, z.toDouble() - gridSize / 2)
                            vec.normalize()
                            rays.add(vec)
                        }
                    }
                }
            }
            return rays
        }

        val world = center.world ?: return arrayListOf()
        val blocksToBlowUp = arrayListOf<Block>()
        val rays = generateRays()

        for (ray in rays) {
            var intensity = power * (0.7f + Math.random() * 0.6f)
            val position = center.clone().toVector()

            while (intensity > 0) {
                val blockLocation = position.toLocation(world)
                val block = world.getBlockAt(blockLocation)

                val isOnCenter = blockLocation.blockX == center.blockX && blockLocation.blockY == center.blockY && blockLocation.blockZ == center.blockZ
                if ((!fromBlock || !isOnCenter) && block.type != Material.AIR) {
                    val blastResistance = block.blastResistance
                    intensity -= (blastResistance + 0.3f) * 0.3f

                    if (intensity > 0) {
                        if (!blocksToBlowUp.contains(block)) {
                            blocksToBlowUp.add(block)
                        }
                    }
                }

                position.add(ray.clone().multiply(0.3))
                intensity -= 0.22500001f
            }
        }

        return blocksToBlowUp
    }
}