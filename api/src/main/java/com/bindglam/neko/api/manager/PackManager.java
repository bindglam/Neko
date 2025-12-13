package com.bindglam.neko.api.manager;

import com.bindglam.neko.api.pack.host.PackHost;
import net.kyori.adventure.resource.ResourcePackInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;

public interface PackManager extends ManagerBase, Reloadable {
    File BUILD_ZIP = new File("plugins/Neko/build.zip");

    @NotNull ResourcePackInfo packInfo(URI uri);

    @Nullable PackHost packHost();

    void sendPack(Player player);
}
