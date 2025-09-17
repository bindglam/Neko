package com.bindglam.neko.api.manager;

import com.bindglam.neko.api.data.Cache;
import org.jetbrains.annotations.NotNull;

public interface CacheManager extends ManagerBase {
    @NotNull Cache getCache(@NotNull String name);
}
