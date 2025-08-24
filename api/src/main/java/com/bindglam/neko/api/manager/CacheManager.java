package com.bindglam.neko.api.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public interface CacheManager extends ManagerBase {
    @Nullable InputStream getCache(@NotNull String path);
}
