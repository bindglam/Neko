package com.bindglam.neko.manager

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
                        NekoProvider.neko().reload(ctx.source.sender)

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

                                    BuiltInRegistries.ITEMS.get(key).ifPresentOrElse({ customItem ->
                                        player.inventory.addItem(customItem.itemStack().apply { this.amount = amount })
                                        ctx.source.sender.sendMessage(Component.text("Successfully gave an item").color(NamedTextColor.GREEN))
                                    }) {
                                        ctx.source.sender.sendMessage(Component.text("Unknown item key").color(NamedTextColor.RED))
                                    }

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

                            BuiltInRegistries.GLYPHS.get(key).ifPresentOrElse({ glyph ->
                                ctx.source.sender.sendMessage(glyph.component(GlyphBuilder.builder().offsetX(0)))
                            }) {
                                ctx.source.sender.sendMessage(Component.text("Unknown glyph key").color(NamedTextColor.RED))
                            }

                            return@executes Command.SINGLE_SUCCESS
                        })
                )
                .also { commands.registrar().register(it.build()) }
        }
    }
}