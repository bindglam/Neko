package io.github.bindglam.neko.utils

import io.github.bindglam.neko.Neko
import io.github.bindglam.neko.NekoPlugin
import org.bukkit.plugin.java.JavaPlugin

fun NekoPlugin.plugin() = this as JavaPlugin

fun logger() = Neko.plugin().plugin().logger