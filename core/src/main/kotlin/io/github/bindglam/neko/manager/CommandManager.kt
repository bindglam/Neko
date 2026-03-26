package io.github.bindglam.neko.manager

import io.github.bindglam.neko.Neko
import io.github.bindglam.neko.registry.RegistriesImpl
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.bukkit.CloudBukkitCapabilities
import org.incendo.cloud.bukkit.parser.NamespacedKeyParser
import org.incendo.cloud.bukkit.parser.PlayerParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager
import org.incendo.cloud.permission.Permission

object CommandManager : Managerial {
    override fun preload(context: Context) {
        val manager = LegacyPaperCommandManager(
            context.plugin(),
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.identity()
        )

        if (manager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            manager.registerBrigadier()
        } else if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            manager.registerAsynchronousCompletions()
        }

        manager.command(manager.commandBuilder("neko")
            .literal("reload")
            .permission(Permission.of("mint.command.reload"))
            .handler { ctx ->
                ctx.sender().sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW))

                Neko.plugin().reload()

                ctx.sender().sendMessage(Component.text("Successfully reloaded!").color(NamedTextColor.GREEN))
            })

        manager.command(manager.commandBuilder("neko")
            .literal("give")
            .permission(Permission.of("mint.command.give"))
            .required("player", PlayerParser.playerParser())
            .required("item", NamespacedKeyParser.namespacedKeyParser())
            .handler { ctx ->
                val player = ctx.get<Player>("player")
                val item = RegistryManager.GlobalRegistries.registries().item().get(ctx.get<NamespacedKey>("item")).orElse(null)
                if (item == null) {
                    ctx.sender().sendMessage(Component.text("Item not found").color(NamedTextColor.RED))
                    return@handler
                }

                player.inventory.addItem(item.itemStack().get())
            })
    }

    override fun start(context: Context) {
    }

    override fun end(context: Context) {
    }
}