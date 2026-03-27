package io.github.bindglam.neko;

import io.github.bindglam.neko.manager.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class NekoPluginImpl extends JavaPlugin implements NekoPlugin {
    @Getter @Accessors(fluent = true)
    private final RegistryManagerImpl registryManager = new RegistryManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ContentManagerImpl contentManager = new ContentManagerImpl();
    @Getter @Accessors(fluent = true)
    private final ResourcePackManagerImpl resourcePackManager = new ResourcePackManagerImpl();

    private final CommandManager commandManager = new CommandManager();

    private final List<Managerial> managers = List.of(
            registryManager,
            contentManager,
            resourcePackManager,
            commandManager
    );

    @Override
    public void onEnable() {
        Neko.registerPlugin(this);

        Context context = new Context(this);
        managers.forEach(manager -> manager.preload(context));

        getServer().getPluginManager().registerEvents(new ServerLoadListener(), this);
    }

    @Override
    public void onDisable() {
        Context context = new Context(this);
        managers.forEach(manager -> manager.end(context));

        Neko.unregisterPlugin();
    }

    @Override
    public void reload() {
        List<Reloadable> reloadableList = managers.stream()
                .filter(manager -> manager instanceof Reloadable)
                .map(manager -> (Reloadable) manager)
                .toList();

        Context context = new Context(this);
        reloadableList.forEach(reloadable -> reloadable.end(context));
        reloadableList.forEach(reloadable -> reloadable.preload(context));
        reloadableList.forEach(reloadable -> reloadable.start(context));
    }

    private final class ServerLoadListener implements Listener {
        @EventHandler
        public void onServerLoad(ServerLoadEvent event) {
            if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
                return;
            }

            Context context = new Context(NekoPluginImpl.this);
            managers.forEach(manager -> manager.start(context));
        }
    }
}
