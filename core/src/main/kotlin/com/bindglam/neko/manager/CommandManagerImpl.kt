package com.bindglam.neko.manager

import com.bindglam.neko.api.Neko
import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.manager.CommandManager
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.plugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandPermission
import dev.jorel.commandapi.arguments.NamespacedKeyArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

object CommandManagerImpl : CommandManager {
    override fun start() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(NekoProvider.neko().plugin()).silentLogs(true))

        CommandAPICommand("neko")
            .withPermission(CommandPermission.OP)
            .withSubcommands(
                CommandAPICommand("reload")
                    .executes(CommandExecutor { sender, args ->
                        sender.sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW))
                        NekoProvider.neko().reload().also { info ->
                            if(info == Neko.ReloadInfo.SUCCESS)
                                sender.sendMessage(Component.text("Successfully reloaded!").color(NamedTextColor.GREEN))
                            else
                                sender.sendMessage(Component.text("Failed to reload").color(NamedTextColor.RED))
                        }
                    }),
                CommandAPICommand("give")
                    .withArguments(PlayerArgument("player"), NamespacedKeyArgument("key"))
                    .executes(CommandExecutor { sender, args ->
                        val player = args["player"] as Player
                        val key = args["key"] as NamespacedKey

                        val customItem = BuiltInRegistries.ITEMS.getOrNull(key)

                        if(customItem == null) {
                            sender.sendMessage(Component.text("Unknown item key").color(NamedTextColor.RED))
                            return@CommandExecutor
                        }

                        player.inventory.addItem(customItem.itemStack())
                        sender.sendMessage(Component.text("Successfully gave an item").color(NamedTextColor.GREEN))
                    }),
                CommandAPICommand("glyph")
                    .withArguments(NamespacedKeyArgument("key"))
                    .executes(CommandExecutor { sender, args ->
                        val key = args["key"] as NamespacedKey

                        val glyph = BuiltInRegistries.GLYPHS.getOrNull(key)

                        if(glyph == null) {
                            sender.sendMessage(Component.text("Unknown glyph key").color(NamedTextColor.RED))
                            return@CommandExecutor
                        }

                        sender.sendMessage(glyph.component(GlyphBuilder(16)))
                    })
            )
            .register()

        CommandAPI.onEnable()
    }

    override fun end() {
        CommandAPI.onDisable()
    }
}