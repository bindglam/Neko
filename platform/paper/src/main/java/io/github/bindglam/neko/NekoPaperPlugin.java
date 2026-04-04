package io.github.bindglam.neko;

import io.github.bindglam.neko.manager.*;
import io.github.bindglam.neko.platform.PaperAdapter;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public final class NekoPaperPlugin extends JavaPlugin implements NekoPlatform {
    @Getter @Accessors(fluent = true)
    private final PaperAdapter platformAdapter = new PaperAdapter();

    @Getter @Accessors(fluent = true)
    private final RegistryManagerImpl registryManager = new RegistryManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ContentManagerImpl contentManager = new ContentManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ResourcePackManagerImpl resourcePackManager = new ResourcePackManagerImpl();

    private final CommandManager commandManager = new CommandManager();

    private final List<Managerial<NekoPaperPlugin>> managers = List.of(
            registryManager,
            contentManager,
            resourcePackManager,
            commandManager
    );

    @Override
    public void onEnable() {
        Neko.registerPlugin(this);

        Context<NekoPaperPlugin> context = new Context<>(this);
        managers.forEach(manager -> manager.preload(context));

        getServer().getPluginManager().registerEvents(new ServerLoadListener(), this);
    }

    @Override
    public void onDisable() {
        Context<NekoPaperPlugin> context = new Context<>(this);
        managers.forEach(manager -> manager.end(context));

        Neko.unregisterPlugin();
    }

    @Override
    public void reload() {
        List<Reloadable<NekoPaperPlugin>> reloadableList = managers.stream()
                .filter(manager -> manager instanceof Reloadable)
                .map(manager -> (Reloadable<NekoPaperPlugin>) manager)
                .toList();

        Context<NekoPaperPlugin> context = new Context<>(this);
        reloadableList.forEach(reloadable -> reloadable.end(context));
        reloadableList.forEach(reloadable -> reloadable.preload(context));
        reloadableList.forEach(reloadable -> reloadable.start(context));
    }

    @Override
    public @NotNull Logger logger() {
        return getLogger();
    }

    private final class ServerLoadListener implements Listener {
        @EventHandler
        public void onServerLoad(ServerLoadEvent event) {
            if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
                return;
            }

            Context<NekoPaperPlugin> context = new Context<>(NekoPaperPlugin.this);
            managers.forEach(manager -> manager.start(context));
        }
    }
}
