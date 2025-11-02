package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.ManagerBase
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.utils.parallelIOThreadPool
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import java.util.function.Consumer
import java.util.function.Function

class ShutdownProcess : Process {
    private val parallelThreadPool = parallelIOThreadPool()

    override fun start(list: List<ManagerBase>) {
        list.forEach {
            it.end(this)
        }
    }

    override fun close() {
        parallelThreadPool.close()
    }

    override fun <T : Any> forEachParallel(list: List<T>, sizeAssume: Function<T, Long>, block: Consumer<T>) {
        parallelThreadPool.forEachParallel(list, { e -> sizeAssume.apply(e) }) { block.accept(it) }
    }
}