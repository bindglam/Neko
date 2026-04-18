package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.NekoPaperPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.bukkit.parser.NamespacedKeyParser;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class CommandManager implements Managerial {
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    @Override
    public void preload(@NotNull Context context) {
        LegacyPaperCommandManager<org.bukkit.command.CommandSender> manager = new LegacyPaperCommandManager<>(
                (NekoPaperPlugin) context.platform(),
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );

        if (manager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            manager.registerBrigadier();
        } else if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            manager.registerAsynchronousCompletions();
        }

        manager.command(manager.commandBuilder("neko")
                .literal("reload")
                .permission(Permission.of("mint.command.reload"))
                .handler(ctx -> {
                    ctx.sender().sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW));
                    Neko.platform().reload();
                    ctx.sender().sendMessage(Component.text("Successfully reloaded!").color(NamedTextColor.GREEN));
                }));

        manager.command(manager.commandBuilder("neko")
                .literal("give")
                .permission(Permission.of("mint.command.give"))
                .required("player", PlayerParser.playerParser())
                .required("item", NamespacedKeyParser.namespacedKeyParser())
                .handler(ctx -> {
                    Player player = ctx.get("player");
                    NamespacedKey itemKey = ctx.get("item");

                    var item = RegistryManager.GlobalRegistries.registries().item()
                            .get(itemKey)
                            .orElse(null);
                    if (item == null) {
                        ctx.sender().sendMessage(Component.text("Item not found").color(NamedTextColor.RED));
                        return;
                    }

                    player.getInventory().addItem((ItemStack) item.itemStack().get().unwrap());
                }));
    }

    @Override
    public void start(@NotNull Context context) {
    }

    @Override
    public void end(@NotNull Context context) {
    }
}
