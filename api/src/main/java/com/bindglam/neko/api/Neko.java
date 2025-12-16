package com.bindglam.neko.api;

import com.bindglam.neko.api.manager.CacheManager;
import com.bindglam.neko.api.manager.ContentManager;
import com.bindglam.neko.api.manager.PackManager;
import com.bindglam.neko.api.manager.PlayerNetworkManager;
import com.bindglam.neko.api.nms.NMSHook;
import com.bindglam.neko.api.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Neko {
    void reload(CommandSender sender);

    default void reload() {
        reload(Bukkit.getConsoleSender());
    }

    @NotNull CacheManager cacheManager();

    @NotNull ContentManager contentManager();

    @NotNull PackManager packManager();

    @NotNull PlayerNetworkManager playerNetworkManager();

    @NotNull NMSHook nms();

    @NotNull Scheduler scheduler();
}
