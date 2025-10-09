package com.bindglam.neko.manager

import com.bindglam.neko.api.Neko
import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey

@Suppress("unstableApiUsage")
object CommandManager {
    fun init(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            Commands.literal("neko")
                .requires { it.sender.isOp }
                .then(Commands.literal("reload")
                    .executes { ctx ->
                        ctx.source.sender.sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW))
                        NekoProvider.neko().reload(ctx.source.sender).also { info ->
                            if(info == Neko.ReloadInfo.SUCCESS)
                                ctx.source.sender.sendMessage(Component.text("Successfully reloaded!").color(NamedTextColor.GREEN))
                            else
                                ctx.source.sender.sendMessage(Component.text("Failed to reload").color(NamedTextColor.RED))
                        }

                        return@executes Command.SINGLE_SUCCESS
                    }
                )
                .then(Commands.literal("give")
                    .then(Commands.argument("player", ArgumentTypes.player())
                        .then(Commands.argument("key", ArgumentTypes.namespacedKey())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes { ctx ->
                                    val player = ctx.getArgument("player", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source).first()
                                    val key = ctx.getArgument("key", NamespacedKey::class.java)
                                    val amount = ctx.getArgument("amount", Int::class.java)

                                    val customItem = BuiltInRegistries.ITEMS.getOrNull(key)

                                    if(customItem == null) {
                                        ctx.source.sender.sendMessage(Component.text("Unknown item key").color(NamedTextColor.RED))
                                        return@executes Command.SINGLE_SUCCESS
                                    }

                                    player.inventory.addItem(customItem.itemStack().apply { this.amount = amount })
                                    ctx.source.sender.sendMessage(Component.text("Successfully gave an item").color(NamedTextColor.GREEN))

                                    return@executes Command.SINGLE_SUCCESS
                                }
                            )
                        )
                    )
                )
                .then(Commands.literal("glyph")
                    .then(Commands.argument("key", ArgumentTypes.namespacedKey())
                        .executes { ctx ->
                            val key = ctx.getArgument("key", NamespacedKey::class.java)

                            val glyph = BuiltInRegistries.GLYPHS.getOrNull(key)

                            if(glyph == null) {
                                ctx.source.sender.sendMessage(Component.text("Unknown glyph key").color(NamedTextColor.RED))
                                return@executes Command.SINGLE_SUCCESS
                            }

                            ctx.source.sender.sendMessage(glyph.component(GlyphBuilder.builder().offsetX(0)))
                            return@executes Command.SINGLE_SUCCESS
                        })
                )
                .also { commands.registrar().register(it.build()) }
        }
    }
}