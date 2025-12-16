package com.bindglam.neko.scheduler

import com.bindglam.neko.api.scheduler.Scheduler
import com.bindglam.neko.api.scheduler.SchedulerTask
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

class PaperScheduler(private val plugin: JavaPlugin) : Scheduler {
    override fun run(action: Runnable): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getGlobalRegionScheduler().run(plugin) { action.run() }
    )

    override fun runLater(action: Runnable, ticks: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getGlobalRegionScheduler().runDelayed(plugin, { action.run() }, ticks)
    )

    override fun runTimer(action: Runnable, delay: Long, period: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, { action.run() }, delay, period)
    )

    override fun run(entity: Entity, action: Runnable): SchedulerTask = SchedulerTaskImpl(
        entity.scheduler.run(plugin, { action.run() }, {})!!
    )

    override fun runLater(entity: Entity, action: Runnable, ticks: Long): SchedulerTask = SchedulerTaskImpl(
        entity.scheduler.runDelayed(plugin, { action.run() }, {}, ticks)!!
    )

    override fun runTimer(entity: Entity, action: Runnable, delay: Long, period: Long): SchedulerTask = SchedulerTaskImpl(
        entity.scheduler.runAtFixedRate(plugin, { action.run() }, {}, delay, period)!!
    )

    override fun run(location: Location, action: Runnable): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getRegionScheduler().run(plugin, location) { action.run() }
    )

    override fun runLater(location: Location, action: Runnable, ticks: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getRegionScheduler().runDelayed(plugin, location, { action.run() }, ticks)
    )

    override fun runTimer(location: Location, action: Runnable, delay: Long, period: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getRegionScheduler().runAtFixedRate(plugin, location, { action.run() }, delay, period)
    )

    override fun runAsync(action: Runnable): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getAsyncScheduler().runNow(plugin) { action.run() }
    )

    override fun runAsyncLater(action: Runnable, ticks: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getAsyncScheduler().runDelayed(plugin, { action.run() }, ticks * 50L, TimeUnit.MILLISECONDS)
    )

    override fun runAsyncTimer(action: Runnable, delay: Long, period: Long): SchedulerTask = SchedulerTaskImpl(
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, { action.run() }, delay * 50L, period * 50L, TimeUnit.MILLISECONDS)
    )

    private class SchedulerTaskImpl(private val task: ScheduledTask) : SchedulerTask {
        override fun cancel() {
            task.cancel()
        }
    }
}