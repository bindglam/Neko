package com.bindglam.neko.api.manager;

import com.bindglam.neko.api.pack.host.PackHost;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.resource.ResourcePackInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;

public interface PackManager extends ManagerBase, Reloadable {
    File BUILD_ZIP = new File("plugins/Neko/build.zip");

    void pack();

    @NotNull File getFile(@NotNull String path);

    default @NotNull File getFile(@NotNull Key key, @NotNull String extension) {
        return getFile("assets/" + key.namespace() + "/" + key.value() + "." + extension);
    }

    @NotNull ResourcePackInfo packInfo(URI uri);

    @Nullable PackHost packHost();
}
