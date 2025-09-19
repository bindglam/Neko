package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.ManagerBase
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.utils.parallelIOThreadPool
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import java.util.function.Consumer
import java.util.function.Function

class ReloadProcess(private val sender: CommandSender) : Process {
    private val parallelThreadPool = parallelIOThreadPool()

    private var status = "Preparing..."

    private var progress = 0
    private var goal = 0

    private val bar = BossBar.bossBar(Component.text(status), 0f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS).also { sender.showBossBar(it) }

    override fun start(list: List<ManagerBase>) {
        goal = list.size

        list.forEach {
            progress++
            status = "Reloading... ( $progress / $goal )"

            it.end(this)
            it.start(this)

            bar.name(Component.text(status))
            bar.progress(progress.toFloat() / goal)
        }

        sender.hideBossBar(bar)
    }

    override fun close() {
        parallelThreadPool.close()
    }

    override fun <T : Any> forEachParallel(list: List<T>, sizeAssume: Function<T, Long>, block: Consumer<T>) {
        parallelThreadPool.forEachParallel(list, { sizeAssume.apply(it) }, { block.accept(it) })
    }
}