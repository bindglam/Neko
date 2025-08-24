package com.bindglam.neko.api.manager;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface PackManager extends ManagerBase, Reloadable {
    void pack();

    @NotNull File getFile(@NotNull String path);

    default @NotNull File getFile(@NotNull Key key, @NotNull String extension) {
        return getFile("assets/" + key.namespace() + "/" + key.value() + "." + extension);
    }

    @NotNull File getGeneratedFile(@NotNull String path);

    default @NotNull File getGeneratedFile(@NotNull Key key, @NotNull String extension) {
        return getGeneratedFile("assets/" + key.namespace() + "/" + key.value() + "." + extension);
    }
}
