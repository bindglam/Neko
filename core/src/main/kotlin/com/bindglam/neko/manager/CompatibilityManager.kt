package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.ManagerBase
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.compatibility.Compatibility
import com.bindglam.neko.compatibility.placeholderapi.PlaceholderAPICompatibility
import org.bukkit.Bukkit

object CompatibilityManager : ManagerBase {
    private val compatibilities = listOf(PlaceholderAPICompatibility)

    private val enabledCompatibilities = arrayListOf<Compatibility>()

    override fun start(process: Process) {
        compatibilities.forEach { compat ->
            if(!Bukkit.getPluginManager().isPluginEnabled(compat.requiredPlugin)) return@forEach

            compat.start()

            enabledCompatibilities.add(compat)
        }
    }

    override fun end(process: Process) {
        enabledCompatibilities.forEach { compat ->
            compat.end()
        }
    }
}