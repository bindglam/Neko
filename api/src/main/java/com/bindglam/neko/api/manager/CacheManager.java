package com.bindglam.neko.api.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.function.Consumer;

public interface CacheManager extends ManagerBase {
    @Nullable File getCache(@NotNull String path);

    void saveCache(@NotNull String path, @NotNull Consumer<File> consumer);
}
