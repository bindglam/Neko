package io.github.bindglam.neko;

import io.github.bindglam.neko.manager.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public final class NekoPluginImpl extends JavaPlugin implements NekoPlugin {
    private final List<Managerial> managers = List.of(
            RegistryManagerImpl.INSTANCE,
            ContentManagerImpl.INSTANCE,
            ResourcePackManagerImpl.INSTANCE,
            CommandManager.INSTANCE
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

    @Override
    public @NotNull RegistryManager registryManager() {
        return RegistryManagerImpl.INSTANCE;
    }

    @Override
    public @NotNull ContentManager contentManager() {
        return ContentManagerImpl.INSTANCE;
    }

    @Override
    public @NotNull ResourcePackManager resourcePackManager() {
        return ResourcePackManagerImpl.INSTANCE;
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
